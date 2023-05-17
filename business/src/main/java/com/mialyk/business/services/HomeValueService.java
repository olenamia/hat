package com.mialyk.business.services;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mialyk.business.dtos.AnalyticsDto;
import com.mialyk.business.dtos.HomeValueDto;
import com.mialyk.business.mappers.AnalyticsDtoMapper;
import com.mialyk.persistence.repositories.HomeValueRepository;
import com.mialyk.persistence.views.StateAnalyticsView;

import org.postgresql.util.PGobject;

@Service
public class HomeValueService implements IHomeValueService {
    @Autowired
    private HomeValueRepository homeValueZillowRepository;
    @Autowired
    private AnalyticsDtoMapper analyticsDtoMapper;

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
                } 
                catch (ParseException e) {
                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    @Transactional
    public List<HomeValueDto> getHistoricalDataUS() {

        List<Object[]> homeValuesList = homeValueZillowRepository.getYearlyHomeValuesByUS();
        return homeValuesList.stream().map(homeValue -> {
            try {
                return new HomeValueDto(
                    (PGobject)homeValue[0], 
                    homeValue[1] == null ? null : ((BigDecimal)homeValue[1]).doubleValue());
            }
            catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<HomeValueDto> getHistoricalDataByCountyRegionId(Integer regionId) {
        List<Object[]> homeValuesList = homeValueZillowRepository.getYearlyHomeValuesByCountyRegionId(regionId);
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
    public List<HomeValueDto> getHistoricalDataByMetroRegionId(Integer regionId) {
        List<Object[]> homeValuesList = homeValueZillowRepository.getYearlyHomeValuesByMetroRegionId(regionId);
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
        List<StateAnalyticsView> analyticsList = homeValueZillowRepository.GetAnalyticsForStates();

        return analyticsList.stream().map(analyticsView -> {
            return analyticsDtoMapper.map(analyticsView);
        }).collect(Collectors.toList());
    }
}
