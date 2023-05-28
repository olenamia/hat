package com.mialyk.business.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mialyk.business.dtos.AnalyticsDto;
import com.mialyk.business.dtos.HomeValueDto;
import com.mialyk.business.mappers.AnalyticsMapper;
import com.mialyk.business.mappers.HomeValueMapper;
import com.mialyk.persistence.projections.HistoricalTrendsProjection;
import com.mialyk.persistence.projections.StateAnalyticsProjection;
import com.mialyk.persistence.repositories.HomeValueRepository;

@Service
public class HomeValueServiceImpl implements HomeValueService {
    @Autowired
    private HomeValueRepository homeValueZillowRepository;
    @Autowired
    private AnalyticsMapper analyticsDtoMapper;
    @Autowired
    private HomeValueMapper homeValueDtoMapper;

    @Override
    @Transactional
    public List<HomeValueDto> getHomeValuesByState(String stateName) {

        List<HistoricalTrendsProjection> historicalTrendsViewList = homeValueZillowRepository.getYearlyHomeValuesByState(stateName);

        return historicalTrendsViewList.stream().map(historicalTrendsView -> {
            return homeValueDtoMapper.map(historicalTrendsView);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<HomeValueDto> getHistoricalDataUS() {

        List<HistoricalTrendsProjection> historicalTrendsViewList = homeValueZillowRepository.getYearlyHomeValuesByUS();

        return historicalTrendsViewList.stream().map(historicalTrendsView -> {
            return homeValueDtoMapper.map(historicalTrendsView);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<HomeValueDto> getHistoricalDataByCountyRegionId(Integer regionId) {

        List<HistoricalTrendsProjection> historicalTrendsViewList = homeValueZillowRepository.getYearlyHomeValuesByCountyRegionId(regionId);

        return historicalTrendsViewList.stream().map(historicalTrendsView -> {
            return homeValueDtoMapper.map(historicalTrendsView);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<HomeValueDto> getHistoricalDataByMetroRegionId(Integer regionId) {

        List<HistoricalTrendsProjection> historicalTrendsViewList = homeValueZillowRepository.getYearlyHomeValuesByMetroRegionId(regionId);
    
        return historicalTrendsViewList.stream().map(historicalTrendsView -> {
            return homeValueDtoMapper.map(historicalTrendsView);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<AnalyticsDto> GetAnalyticsForStates() {

        List<StateAnalyticsProjection> analyticsList = homeValueZillowRepository.GetAnalyticsForStates();

        return analyticsList.stream().map(analyticsView -> {
            return analyticsDtoMapper.map(analyticsView);
        }).collect(Collectors.toList());
    }
}
