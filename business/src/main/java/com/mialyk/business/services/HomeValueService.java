package com.mialyk.business.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mialyk.business.dtos.AnalyticsDto;
import com.mialyk.business.dtos.HomeValueDto;
import com.mialyk.business.dtos.StateDto;
import com.mialyk.persistence.entities.RegionType;
import com.mialyk.persistence.entities.Country;
import com.mialyk.persistence.repositories.CountryRepository;
import com.mialyk.persistence.repositories.HomeValueRepository;

import org.postgresql.util.PGobject;

@Service
public class HomeValueService implements IHomeValueService {
    @Autowired
    private HomeValueRepository homeValueZillowRepository;
    @Autowired
    private CountryRepository countryRepository;

    @Override
    public Date getMaxDateByStateName(String stateName) {
        return homeValueZillowRepository.findMaxDateByStateName(stateName);
    }

    @Override
    @Transactional
    public List<HomeValueDto> getHomeValuesByState(String stateName) {

        Date maxDateByState = homeValueZillowRepository.findMaxDateByStateName(stateName);
        if (maxDateByState != null){
            List<Object[]> homeValuesList = homeValueZillowRepository.getYearlyHomeValuesByState(stateName);

            return homeValuesList.stream().map(homeValue -> {
                try {
                    return new HomeValueDto(
                        (PGobject)homeValue[0], 
                        homeValue[1] == null ? null : ((BigDecimal)homeValue[1]).doubleValue());
                } catch (ParseException e) {

                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public List<HomeValueDto> getHistoricalDataUS(String regionName) {
        Optional<Country> countryOptional = countryRepository.findByRegionName(regionName);
        Date maxDateByState = null; 
        if (countryOptional.isPresent()) {
            maxDateByState =  homeValueZillowRepository.findMaxDateByRegionIdAndRegionType(countryOptional.get().getRegionId(), RegionType.COUNTRY.name());
        }
 
        if (maxDateByState != null){
            List<Object[]> homeValuesList = homeValueZillowRepository.getYearlyHomeValuesByRegionIdAndRegionType(countryOptional.get().getRegionId(), RegionType.COUNTRY.name());
            return homeValuesList.stream().map(homeValue -> {
                try {
                    return new HomeValueDto(
                        (PGobject)homeValue[0], 
                        homeValue[1] == null ? null : ((BigDecimal)homeValue[1]).doubleValue());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public List<HomeValueDto> getHistoricalDataByRegionIdAndRegioType(Integer regionId, RegionType regionType) {
        List<Object[]> homeValuesList = homeValueZillowRepository.getYearlyHomeValuesByRegionIdAndRegionType(regionId, regionType.name());
        return homeValuesList.stream().map(homeValue -> {
            try {
                return new HomeValueDto(
                    (PGobject)homeValue[0], 
                    homeValue[1] == null ? null : ((BigDecimal)homeValue[1]).doubleValue());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<AnalyticsDto> GetAnalyticsForStates() {
  
        List<AnalyticsDto> analyticsList =  new ArrayList<>();
        for (Object[] row : homeValueZillowRepository.GetAnalyticsForStates()) {

            Date date = (Date) row[0];
            Double homeValue = ((BigDecimal)row[1]).doubleValue();

            PGobject pgObject = ((PGobject)row[2]);
            String[] values = pgObject.getValue().substring(1, pgObject.getValue().length() - 1).split(",");
            StateDto regionDto = new StateDto(Integer.parseInt(values[1]), values[2].replace("\"", ""), values[4]);

            Date prevYearDate = (Date) row[3];
            Double prevYearValue = ((BigDecimal)row[4]).doubleValue();

            DecimalFormat format = new DecimalFormat("#.##");
            format.setRoundingMode(RoundingMode.HALF_UP);

            Double yoyChange = Double.valueOf(format.format(((BigDecimal)row[5])));
            Double momChange = Double.valueOf(format.format(((BigDecimal)row[6])));
            Date prevMonthDate = (Date) row[7];
            Double prevMonthValue =((BigDecimal)row[8]).doubleValue();

            AnalyticsDto analytics = new AnalyticsDto(date, homeValue, regionDto, prevYearDate, prevYearValue, yoyChange, momChange, prevMonthDate, prevMonthValue);
            analyticsList.add(analytics);
        }
        return analyticsList;
    }
}