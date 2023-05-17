package com.mialyk.business.services;

import java.util.List;

import com.mialyk.business.dtos.AnalyticsDto;
import com.mialyk.business.dtos.HomeValueDto;

public interface IHomeValueService {

    List<HomeValueDto> getHomeValuesByState(String stateName);

    List<HomeValueDto> getHistoricalDataUS();

    List<HomeValueDto> getHistoricalDataByCountyRegionId(Integer regionId);

    List<HomeValueDto> getHistoricalDataByMetroRegionId(Integer regionId);

    List<AnalyticsDto> GetAnalyticsForStates();

}