package com.mialyk.business.mappers;

import org.springframework.stereotype.Component;

import com.mialyk.business.common.Utils;
import com.mialyk.business.dtos.AnalyticsDto;
import com.mialyk.business.dtos.RegionDto;
import com.mialyk.business.dtos.StateDto;
import com.mialyk.persistence.projections.StateAnalyticsProjection;

@Component
public class AnalyticsMapper {

    public AnalyticsDto map(StateAnalyticsProjection analyticsView) {
        RegionDto regionDto = new StateDto(analyticsView.getStateRegionId(), analyticsView.getStateRegionName(), analyticsView.getStateShortName());

        return new AnalyticsDto(
            analyticsView.getDate(), 
            analyticsView.getValue(), 
            regionDto, 
            analyticsView.getPrevYearDate(), 
            analyticsView.getPrevYearValue(), 
            analyticsView.getYoyChange() == null ? null : Utils.roundDouble(analyticsView.getYoyChange()), 
            analyticsView.getMomChange() == null ? null : Utils.roundDouble(analyticsView.getMomChange()), 
            analyticsView.getPrevMonthDate(), 
            analyticsView.getPrevMonthValue());
    }
}
