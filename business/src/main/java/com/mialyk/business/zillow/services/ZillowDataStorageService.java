package com.mialyk.business.zillow.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mialyk.persistence.entities.*;
import com.mialyk.persistence.entities.RegionType;
import com.mialyk.persistence.repositories.HomeValueRepository;
import com.mialyk.business.services.ICountryService;
import com.mialyk.business.services.ICountyService;
import com.mialyk.business.services.IMetroAreaService;
import com.mialyk.business.services.IStateService;
import com.mialyk.business.zillow.dtos.ZillowHomeValueDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ZillowDataStorageService {
    @Autowired
    private HomeValueRepository homeValueRepository;
    @Autowired
    private ICountryService countryService;
    @Autowired
    private IStateService stateService;
    @Autowired
    private ICountyService countyService;
    @Autowired
    private IMetroAreaService metroAreaService;
    @Autowired
    private ZillowDataMapper zillowDataMapper;

    @Transactional
    public void saveHomeValues(List<ZillowHomeValueDto> zillowHomeValueDtos) {
        List<HomeValue> homeValueZillowList = new ArrayList<>();

        for (ZillowHomeValueDto zillowHomeValueDto : zillowHomeValueDtos) {
            RegionType regionType = zillowDataMapper.getRegionType(zillowHomeValueDto.getRegionType());
            Region region = getRegionByZillowRegionType(regionType, zillowHomeValueDto);

            if (region == null) {
                continue;
            }

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
            System.out.println(region.getRegionName());
        }
        System.out.println("Save All");
        homeValueRepository.saveAll(homeValueZillowList);
    }

    private Region getRegionByZillowRegionType(RegionType regionType, ZillowHomeValueDto zillowHomeValueDto) {

        switch(regionType) {
            case STATE:
                return (State)stateService.getOrCreateState(
                    zillowHomeValueDto.getRegionName(), 
                    zillowHomeValueDto.getStateName(), 
                    zillowHomeValueDto.getRegionId(), 
                    zillowHomeValueDto.getSizeRank());
            case COUNTRY:
                return (Country)countryService.getOrCreateCountry(
                    zillowHomeValueDto.getRegionName(), 
                    zillowHomeValueDto.getRegionId(), 
                    zillowHomeValueDto.getSizeRank());
            case COUNTY:
                return (County)countyService.getOrCreateCounty(
                    zillowHomeValueDto.getRegionName(), 
                    zillowHomeValueDto.getStateName(), 
                    zillowHomeValueDto.getRegionId(),
                    zillowHomeValueDto.getSizeRank(),
                    zillowHomeValueDto.getStateCodeFIPS(),
                    zillowHomeValueDto.getMunicipalCodeFIPS(),
                    zillowHomeValueDto.getMetro());
            case METRO:
                return (MetroArea)metroAreaService.getOrCreateMetroArea(
                    zillowHomeValueDto.getRegionName(), 
                    zillowHomeValueDto.getStateName(), 
                    zillowHomeValueDto.getRegionId(), 
                    zillowHomeValueDto.getSizeRank());
            default:
                return null;
        }
    }
}