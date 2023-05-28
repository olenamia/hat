package com.mialyk.business.mappers;

import org.springframework.stereotype.Component;

import com.mialyk.business.common.Utils;
import com.mialyk.business.dtos.HomeValueDto;
import com.mialyk.persistence.entities.HomeValue;
import com.mialyk.persistence.projections.HistoricalTrendsProjection;

@Component
public class HomeValueMapper {
    public HomeValueDto map(HistoricalTrendsProjection historicalTrendsView) {
        HomeValueDto homeValueDto = new HomeValueDto();

        homeValueDto.setId(historicalTrendsView.getId());
        homeValueDto.setHomeValue(historicalTrendsView.getValue());
        homeValueDto.setDate(historicalTrendsView.getDate());
        if (historicalTrendsView.getYoyChange() != null) {
            homeValueDto.setYoyChange(Utils.roundDouble(historicalTrendsView.getYoyChange()));
        }

        return homeValueDto;
    }

    public HomeValueDto map(HomeValue homeValue) {
        HomeValueDto homeValueDto = new HomeValueDto();

        homeValueDto.setId(homeValue.getId());
        homeValueDto.setHomeValue(Utils.roundDouble(homeValue.getValue()));
        homeValueDto.setDate(homeValue.getDate());

        return homeValueDto;
    }
}
