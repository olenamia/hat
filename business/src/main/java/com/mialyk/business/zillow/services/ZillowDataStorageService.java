package com.mialyk.business.zillow.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.transaction.Transactional;

import com.mialyk.persistence.entities.*;
import com.mialyk.persistence.entities.RegionType;
import com.mialyk.persistence.repositories.HomeValueRepository;

import com.mialyk.business.services.CountryService;
import com.mialyk.business.services.CountyService;
import com.mialyk.business.services.MetroAreaService;
import com.mialyk.business.services.StateService;
import com.mialyk.business.zillow.dtos.ZillowHomeValueDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZillowDataStorageService {
    @Autowired
    private HomeValueRepository homeValueRepository;
    @Autowired
    private CountryService countryService;
    @Autowired
    private StateService stateService;
    @Autowired
    private CountyService countyService;
    @Autowired
    private MetroAreaService metroAreaService;

    @Transactional
    public void saveHomeValues(List<ZillowHomeValueDto> zillowHomeValueDtos) {
        List<HomeValue> homeValueZillowList = new ArrayList<>();

        for (ZillowHomeValueDto zillowHomeValueDto : zillowHomeValueDtos) {
            RegionType regionType = mapRegionType(zillowHomeValueDto.getRegionType());
            Region region = getRegionByZillowRegionType(zillowHomeValueDto);
            Date lastAddedDate = homeValueRepository.findMaxDateByRegionIdAndRegionType(region.getRegionId(), regionType.name());

            for(Map.Entry<String, Double> entry : zillowHomeValueDto.getMonthlyData().entries()){
                Double value = entry.getValue();
                Date date = Date.valueOf(entry.getKey());

                if (value != null) {
                    // For dates after last added or if state's values have never been added before
                    if (lastAddedDate == null || date.compareTo(lastAddedDate) > 0) {
                        HomeValue homeValue = new HomeValue();
                        homeValue.setRegionType(regionType);
                        homeValue.setRegion(region);
                        homeValue.setDate(date);
                        homeValue.setValue(new BigDecimal(value));
                        homeValueZillowList.add(homeValue);
                    }
                }
            }
        }
        homeValueRepository.saveAll(homeValueZillowList);
    }

    private Region getRegionByZillowRegionType(ZillowHomeValueDto zillowHomeValueDto) {
        RegionType regionType = mapRegionType( zillowHomeValueDto.getRegionType());
        Region region = null;

        if (regionType == RegionType.STATE) {
            region = (State)stateService.getState(
                            zillowHomeValueDto.getRegionName(), 
                            zillowHomeValueDto.getStateName(), 
                            zillowHomeValueDto.getRegionId(), 
                            zillowHomeValueDto.getSizeRank());
        }
        else if (regionType == RegionType.COUNTY) {
            region = (County)countyService.getCounty(
                            zillowHomeValueDto.getRegionName(), 
                            zillowHomeValueDto.getStateName(), 
                            zillowHomeValueDto.getRegionId(),
                            zillowHomeValueDto.getSizeRank(),
                            zillowHomeValueDto.getStateCodeFIPS(),
                            zillowHomeValueDto.getMunicipalCodeFIPS(),
                            zillowHomeValueDto.getMetro());
        }
        else if (regionType == RegionType.COUNTRY) {
            region = (Country)countryService.getCountry(
                            zillowHomeValueDto.getRegionName(), 
                            zillowHomeValueDto.getRegionId(), 
                            zillowHomeValueDto.getSizeRank());
        }
        else if (regionType == RegionType.METRO) {
            region = (MetroArea)metroAreaService.getMetroArea(
                            zillowHomeValueDto.getRegionName(), 
                            zillowHomeValueDto.getStateName(), 
                            zillowHomeValueDto.getRegionId(), 
                            zillowHomeValueDto.getSizeRank());
        }
        return region;
    }

    public RegionType mapRegionType(String csvRegionType) {
        RegionType regionType = null;
        switch(csvRegionType) {
            case "state":
                regionType = RegionType.STATE;
                break;
            case "county":
                regionType = RegionType.COUNTY;
                break;
            case "country":
                regionType = RegionType.COUNTRY;
                break;
            case "msa":
                regionType = RegionType.METRO;
                break;
        }
        return regionType;
    }
}