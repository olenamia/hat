package com.mialyk.business.mappers;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.stereotype.Component;

import com.mialyk.business.dtos.AnalyticsDto;
import com.mialyk.business.dtos.RegionDto;
import com.mialyk.business.dtos.StateDto;
import com.mialyk.persistence.views.StateAnalyticsView;

@Component
public class AnalyticsDtoMapper {

    public AnalyticsDto map(StateAnalyticsView analyticsView) {

        RegionDto regionDto = new StateDto(analyticsView.getStateRegionId(), analyticsView.getStateRegionName(), analyticsView.getStateShortName());

        return new AnalyticsDto(
            analyticsView.getDate(), 
            analyticsView.getValue(), 
            regionDto, 
            analyticsView.getPrevYearDate(), 
            analyticsView.getPrevYearValue(), 
            roundDouble(analyticsView.getYoyChange()), 
            roundDouble(analyticsView.getMomChange()), 
            analyticsView.getPrevMonthDate(), 
            analyticsView.getPrevMonthValue());
    }

    private Double roundDouble(Double input){
        BigDecimal bd = new BigDecimal(input).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
