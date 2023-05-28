package com.mialyk.business.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mialyk.business.dtos.AnalyticsDto;
import com.mialyk.business.dtos.HistoricalTrendsDto;
import com.mialyk.business.mappers.AnalyticsMapper;
import com.mialyk.business.mappers.HistoricalTrendsMapper;
import com.mialyk.persistence.projections.HistoricalTrendsProjection;
import com.mialyk.persistence.projections.StateAnalyticsProjection;
import com.mialyk.persistence.repositories.HomeValueRepository;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {
    @Autowired
    private HomeValueRepository homeValueRepository;
    @Autowired
    private AnalyticsMapper analyticsDtoMapper;
    @Autowired
    private HistoricalTrendsMapper historicalTrendsMapper;

    @Override
    @Transactional
    public List<HistoricalTrendsDto> getHistoricalTrendsByState(String stateName) {

        List<HistoricalTrendsProjection> historicalTrendsViewList = homeValueRepository.getYearlyHomeValuesByState(stateName);

        return historicalTrendsViewList.stream().map(historicalTrendsView -> {
            return historicalTrendsMapper.map(historicalTrendsView);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<HistoricalTrendsDto> getHistoricalTrendsUS() {

        List<HistoricalTrendsProjection> historicalTrendsViewList = homeValueRepository.getYearlyHomeValuesByUS();

        return historicalTrendsViewList.stream().map(historicalTrendsView -> {
            return historicalTrendsMapper.map(historicalTrendsView);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<HistoricalTrendsDto> getHistoricalTrendsByCountyRegionId(Integer regionId) {

        List<HistoricalTrendsProjection> historicalTrendsViewList = homeValueRepository.getYearlyHomeValuesByCountyRegionId(regionId);

        return historicalTrendsViewList.stream().map(historicalTrendsView -> {
            return historicalTrendsMapper.map(historicalTrendsView);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<HistoricalTrendsDto> getHistoricalTrendsByMetroRegionId(Integer regionId) {

        List<HistoricalTrendsProjection> historicalTrendsViewList = homeValueRepository.getYearlyHomeValuesByMetroRegionId(regionId);
    
        return historicalTrendsViewList.stream().map(historicalTrendsView -> {
            return historicalTrendsMapper.map(historicalTrendsView);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<AnalyticsDto> GetAnalyticsForStates() {

        List<StateAnalyticsProjection> analyticsList = homeValueRepository.GetAnalyticsForStates();

        return analyticsList.stream().map(analyticsView -> {
            return analyticsDtoMapper.map(analyticsView);
        }).collect(Collectors.toList());
    }
}
