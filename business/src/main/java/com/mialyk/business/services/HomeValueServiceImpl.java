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
import com.mialyk.persistence.repositories.HomeValueRepository;
import com.mialyk.persistence.views.HistoricalTrendsView;
import com.mialyk.persistence.views.StateAnalyticsView;

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

        List<HistoricalTrendsView> historicalTrendsViewList = homeValueZillowRepository.getYearlyHomeValuesByState(stateName);

        return historicalTrendsViewList.stream().map(historicalTrendsView -> {
            return homeValueDtoMapper.map(historicalTrendsView);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<HomeValueDto> getHistoricalDataUS() {

        List<HistoricalTrendsView> historicalTrendsViewList = homeValueZillowRepository.getYearlyHomeValuesByUS();

        return historicalTrendsViewList.stream().map(historicalTrendsView -> {
            return homeValueDtoMapper.map(historicalTrendsView);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<HomeValueDto> getHistoricalDataByCountyRegionId(Integer regionId) {

        List<HistoricalTrendsView> historicalTrendsViewList = homeValueZillowRepository.getYearlyHomeValuesByCountyRegionId(regionId);

        return historicalTrendsViewList.stream().map(historicalTrendsView -> {
            return homeValueDtoMapper.map(historicalTrendsView);
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<HomeValueDto> getHistoricalDataByMetroRegionId(Integer regionId) {

        List<HistoricalTrendsView> historicalTrendsViewList = homeValueZillowRepository.getYearlyHomeValuesByMetroRegionId(regionId);
    
        return historicalTrendsViewList.stream().map(historicalTrendsView -> {
            return homeValueDtoMapper.map(historicalTrendsView);
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
