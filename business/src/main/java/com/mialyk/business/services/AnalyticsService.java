package com.mialyk.business.services;

import java.util.List;

import com.mialyk.business.dtos.AnalyticsDto;
import com.mialyk.business.dtos.HistoricalTrendsDto;

public interface AnalyticsService {

    List<HistoricalTrendsDto> getHistoricalTrendsByState(String stateName);

    List<HistoricalTrendsDto> getHistoricalTrendsUS();

    List<HistoricalTrendsDto> getHistoricalTrendsByCountyRegionId(Integer regionId);

    List<HistoricalTrendsDto> getHistoricalTrendsByMetroRegionId(Integer regionId);

    List<AnalyticsDto> GetAnalyticsForStates();
}
