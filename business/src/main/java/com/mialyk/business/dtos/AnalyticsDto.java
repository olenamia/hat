package com.mialyk.business.dtos;

import java.io.Serializable;
import java.sql.Date;

import lombok.NoArgsConstructor;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class AnalyticsDto  implements Serializable {
    private Date date; 
    private Double homeValue; 
    private RegionDto region; 
    private Date prevYearDate; 
    private Double prevYearValue;
    private Double yoyChange; 
    private Double momChange; 
    private Date prevMonthDate; 
    private Double prevMonthValue;
    
    public AnalyticsDto(Date date, Double homeValue, RegionDto region, Date prevYearDate, 
                        Double prevYearValue, Double yoyChange, Double momChange, 
                        Date prevMonthDate, Double prevMonthValue) {
        this.date = date;
        this.homeValue = homeValue;
        this.region = region;
        this.prevYearDate = prevYearDate;
        this.prevYearValue = prevYearValue;
        this.yoyChange = yoyChange;
        this.momChange = momChange;
        this.prevMonthDate = prevMonthDate;
        this.prevMonthValue = prevMonthValue;
    }
}
