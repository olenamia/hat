package com.mialyk.business.mappers;

import org.springframework.stereotype.Component;

import com.mialyk.business.common.Utils;
import com.mialyk.business.dtos.HomeValueDto;
import com.mialyk.persistence.views.HistoricalTrendsView;

@Component
public class HomeValueDtoMapper {
    public HomeValueDto map(HistoricalTrendsView historicalTrendsView) {

        HomeValueDto homeValueDto = new HomeValueDto();

        homeValueDto.setId(historicalTrendsView.getId());
        homeValueDto.setHomeValue(historicalTrendsView.getValue());
        homeValueDto.setDate(historicalTrendsView.getDate());
        if (historicalTrendsView.getYoyChange() != null) {
            homeValueDto.setYoyChange(Utils.roundDouble(historicalTrendsView.getYoyChange()));
        }

        return homeValueDto;
    }
}
