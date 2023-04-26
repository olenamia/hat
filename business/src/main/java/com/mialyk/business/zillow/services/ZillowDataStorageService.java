package com.mialyk.business.zillow.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import com.mialyk.persistence.entities.*;
import com.mialyk.persistence.entities.HomeValue.RegionType;
import com.mialyk.persistence.repositories.HomeValueRepository;
import com.mialyk.business.services.RegionService;
import com.mialyk.business.zillow.dtos.ZvhiStatesDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ZillowDataStorageService {
    @Autowired
    private HomeValueRepository homeValueRepository;
    @Autowired
    private RegionService regionService;

    @Transactional
    public void saveHomeValues(List<ZvhiStatesDto> zhviStatesDtos) {
        List<HomeValue> homeValueZillowList = new ArrayList<>();

        for (ZvhiStatesDto zhviStatesDto : zhviStatesDtos) {
            RegionType regionType = mapRegionType(zhviStatesDto.getRegionType());
            Region region = getRegionByZillowRegionType(zhviStatesDto);
            Date lastAddedDate = homeValueRepository.findMaxDateByRegionIdAndRegionType(region.getRegionId(), regionType.name());

            for(String key : zhviStatesDto.getMonthlyData().keySet()){

                Double value = zhviStatesDto.getMonthlyData().get(key).iterator().next();
                Date date = Date.valueOf(key);
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

    private Region getRegionByZillowRegionType(ZvhiStatesDto zhviStatesDto) {
        RegionType regionType = mapRegionType( zhviStatesDto.getRegionType());
        Region region = null;

        if (regionType == RegionType.STATE) {
            region = (State)regionService.getState(
                            zhviStatesDto.getRegionName(), 
                            zhviStatesDto.getStateName(), 
                            zhviStatesDto.getRegionId(), 
                            zhviStatesDto.getSizeRank());
        }
        else if (regionType == RegionType.COUNTY) {
            region = (County)regionService.getCounty(
                            zhviStatesDto.getRegionName(), 
                            zhviStatesDto.getStateName(), 
                            zhviStatesDto.getRegionId(),
                            zhviStatesDto.getSizeRank(),
                            zhviStatesDto.getStateCodeFIPS(),
                            zhviStatesDto.getMunicipalCodeFIPS(),
                            zhviStatesDto.getMetro());
        }
        else if (regionType == RegionType.COUNTRY) {
            region = (Country)regionService.getCountry(
                            zhviStatesDto.getRegionName(), 
                            zhviStatesDto.getRegionId(), 
                            zhviStatesDto.getSizeRank());
        }
        else if (regionType == RegionType.METRO) {
            regionType = RegionType.METRO;
            region = (MetroArea)regionService.getMetroArea(
                            zhviStatesDto.getRegionName(), 
                            zhviStatesDto.getStateName(), 
                            zhviStatesDto.getRegionId(), 
                            zhviStatesDto.getSizeRank());
        }
        return region;
    }

    // TODO: Move to ZvhiDto
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