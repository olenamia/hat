package com.mialyk.persistence.views;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class StateAnalyticsView implements Serializable {
    private Date date;
    private Double value;
    private String state;
    private Date prevYearDate;
    private Double prevYearValue;
    private Double yoyChange;
    private Double momChange;
    private Date prevMonthDate;
    private Double prevMonthValue;

    public StateAnalyticsView(Date date, Double value, String state,
                                Date prevYearDate, Double prevYearValue,
                                Double yoyChange, Double momChange, Date prevMonthDate,
                                Double prevMonthValue) {
        this.date = date;
        this.value = value;
        this.state = state;
        this.prevYearDate = prevYearDate;
        this.prevYearValue = prevYearValue;
        this.yoyChange = yoyChange;
        this.momChange = momChange;
        this.prevMonthDate = prevMonthDate;
        this.prevMonthValue = prevMonthValue;
    }
}
