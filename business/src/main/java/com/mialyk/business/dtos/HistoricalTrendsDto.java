package com.mialyk.business.dtos;

import java.io.Serializable;
import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class HistoricalTrendsDto implements Serializable {
    private Date date;
    private Double homeValue;
    private Double yoyChange;
}
