package com.mialyk.business.mappers;

import org.springframework.stereotype.Component;

import com.mialyk.business.common.Utils;
import com.mialyk.business.dtos.HistoricalTrendsDto;
import com.mialyk.persistence.projections.HistoricalTrendsProjection;

@Component
public class HistoricalTrendsMapper {
    public HistoricalTrendsDto map(HistoricalTrendsProjection historicalTrendsProjection) {
        HistoricalTrendsDto historicalTrendsDto = new HistoricalTrendsDto();

        historicalTrendsDto.setHomeValue(historicalTrendsProjection.getValue());
        historicalTrendsDto.setDate(historicalTrendsProjection.getDate());
        if (historicalTrendsProjection.getYoyChange() != null) {
            historicalTrendsDto.setYoyChange(Utils.roundDouble(historicalTrendsProjection.getYoyChange()));
        }

        return historicalTrendsDto;
    }
}
