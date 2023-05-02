package com.mialyk.business.dtos;

import java.io.Serializable;
import java.sql.Date;

import org.postgresql.util.PGobject;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;



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
        /*this.region = new RegionDto(
            region.getValue(), // assuming state.getValue() returns the state id
                state.getValue(), // assuming state.getValue() returns the state name
                region.getValue(), // assuming region.getValue() returns the region id
                region.getValue() // assuming region.getValue() returns the region name
        );*/
    }
    
    // getters and setters
}
/*
public class AnalyticsDto implements Serializable {

    private Date date; 
    private Double homeValue; 
    private RegionDto region; 
    private Date prevYearDate; 
    private Double prevYearValue;
    private Double yoyChange; 
    private Double momChange; 
    private Date prevMonthDate; 
    private Double prevMonthValue;

    public AnalyticsDto(Date date, 
                        Double homeValue, 
                        RegionDto region, 
                        Date prevYearDate, 
                        Double prevYearValue,
                        Double yoyChange, 
                        Double momChange, 
                        Date prevMonthDate, 
                        Double prevMonthValue) {
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

}*/
