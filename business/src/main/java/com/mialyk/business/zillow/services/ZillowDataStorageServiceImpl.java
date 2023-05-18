package com.mialyk.business.zillow.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mialyk.persistence.entities.*;
import com.mialyk.persistence.entities.RegionType;
import com.mialyk.persistence.repositories.HomeValueRepository;
import com.mialyk.business.services.CountryService;
import com.mialyk.business.services.CountyService;
import com.mialyk.business.services.MetroAreaService;
import com.mialyk.business.services.StateService;
import com.mialyk.business.zillow.model.ZillowHomeValue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ZillowDataStorageServiceImpl implements ZillowDataStorageService {
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
    @Autowired
    private ZillowDataMapper zillowDataMapper;

    @Override
    @Transactional
    public void saveHomeValues(List<ZillowHomeValue> zillowHomeValueDtos) {
        List<HomeValue> homeValueZillowList = new ArrayList<>();

        for (ZillowHomeValue zillowHomeValueDto : zillowHomeValueDtos) {
            RegionType regionType = zillowDataMapper.getRegionType(zillowHomeValueDto.getRegionType());
            Region region = getRegionByZillowRegionType(regionType, zillowHomeValueDto);

            if (region == null) {
                continue;
            }

            Date lastAddedDate = homeValueRepository.findMaxDateByIdAndRegionType(region.getId(), regionType.name());

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

    private Region getRegionByZillowRegionType(RegionType regionType, ZillowHomeValue zillowHomeValueDto) {

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
