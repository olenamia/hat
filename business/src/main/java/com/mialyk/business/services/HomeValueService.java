package com.mialyk.business.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialyk.business.dtos.HomeValueDto;

import com.mialyk.persistence.entities.Region;
import com.mialyk.persistence.entities.State;
import com.mialyk.persistence.entities.HomeValue.RegionType;
import com.mialyk.persistence.entities.HomeValue;
//import com.mialyk.persistence.entities.HomeValueZillow.RegionType;
import com.mialyk.persistence.repositories.HomeValueRepository;

import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;


@Service
public class HomeValueService {
    @Autowired
    private HomeValueRepository homeValueZillowRepository;
    @Autowired
    private RegionService regionService;

    /*
    @Transactional
    public void saveHomeValues(List<ZHVIStatesDto> zHVIStatesDtos) {
        
        List<HomeValueZillow> homeValueZillowList = new ArrayList<>();

        for (ZHVIStatesDto zHVIStatesDto : zHVIStatesDtos) {

            RegionType regionType = null;
            State state = null; 
            if (RegionType.STATE.name().equalsIgnoreCase(zHVIStatesDto.getRegionType())) {
                regionType = RegionType.STATE;
                state = 
                    (State)regionService.getRegion(
                        zHVIStatesDto.getRegionType(), 
                        zHVIStatesDto.getRegionName(), 
                        zHVIStatesDto.getStateName(), 
                        zHVIStatesDto.getRegionId(), 
                        zHVIStatesDto.getSizeRank());
            }
            Date lastAddedDate = homeValueZillowRepository.findMaxDateByStateName(state.getRegionName());

            for(String key : zHVIStatesDto.getMonthlyData().keySet()){

                Double value = zHVIStatesDto.getMonthlyData().get(key).iterator().next();
                Date date = Date.valueOf(key);
                if (value != null) {
                    // For dates after last added or if state's values have never been added before
                    if (lastAddedDate == null || date.compareTo(lastAddedDate) > 0) {

                        HomeValueZillow homeValueZillow = new HomeValueZillow();
                        homeValueZillow.setRegionType(regionType);
                        homeValueZillow.setState(state);

                        homeValueZillow.setDate(date);
                        homeValueZillow.setValue(new BigDecimal(value));
                        homeValueZillowList.add(homeValueZillow);
                        //homeValueZillowRepository.saveAndFlush(homeValueZillow);
                    }
                }
            }
        }
        // TODO: What is the difference of different types of savings to DB
        homeValueZillowRepository.saveAll(homeValueZillowList);
    }
 */
    public Date getMaxDateByStateName(String stateName) {
        return homeValueZillowRepository.findMaxDateByStateName(stateName);
    }
    @Transactional
    public List<HomeValueDto> getHomeValuesByState(String stateName) {//throws JsonProcessingException, JsonMappingException {

        Date maxDateByState = homeValueZillowRepository.findMaxDateByStateName(stateName);
        if (maxDateByState != null){
            List<Object[]> homeValuesList = homeValueZillowRepository.getYearlyHomeValuesByState(stateName);

            return homeValuesList.stream().map(homeValue -> {
                try {
                    return new HomeValueDto(
                        (PGobject)homeValue[0], 
                        homeValue[1] == null ? null : ((BigDecimal)homeValue[1]).doubleValue(),
                        stateName);
                } catch (ParseException e) {

                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private Date getMaxHomeValueDate (Region region, RegionType regionType) {


        Date lastAddedDate = homeValueZillowRepository.findMaxDateByRegionIdAndRegionType(region.getRegionId(), regionType.name());

        return lastAddedDate;
                /*Date lastAddedDate = null;

        if (region instanceof State) {
            lastAddedDate = homeValueZillowRepository.findMaxDateByStateName(region.getRegionName());
        }
        else if (region instanceof Country) {
            lastAddedDate = homeValueZillowRepository.findMaxDateByCountryName(region.getRegionName());
        }
        else if (region instanceof Metro) {
            lastAddedDate = homeValueZillowRepository.findMaxDateByMetroNameAndStateName(region.getRegionNameAndState());
        }*/

        /* 
        else if (region instanceof County) {
            lastAddedDate = homeValueZillowRepository.findMaxDateByCountyNameAndState(region.getRegionNameAndState());
        }*/

        // homeValueZillowRepository.findMaxDateByRegionNameAndType(state.getRegionName());


    }

}