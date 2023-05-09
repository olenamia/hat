package com.mialyk.business.services;

import java.sql.Date;
import java.util.List;

import com.mialyk.business.dtos.AnalyticsDto;
import com.mialyk.business.dtos.HomeValueDto;
import com.mialyk.persistence.entities.RegionType;

public interface IHomeValueService {

    Date getMaxDateByStateName(String stateName);

    List<HomeValueDto> getHomeValuesByState(String stateName);

    List<HomeValueDto> getHistoricalDataUS(String regionName);

    List<HomeValueDto> getHistoricalDataByRegionIdAndRegioType(Integer regionId, RegionType regionType);

    List<AnalyticsDto> GetAnalyticsForStates();

}