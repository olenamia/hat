package com.mialyk.business.dtos;

import java.sql.Date;

import lombok.Data;

import java.io.Serializable;
@Data
public class HomeValueCurrentDto  implements Serializable {
    private int region_id;
    private Date date;
    private Date yearBackDate;
    private Date monthBackDate;
    private Double homeValue;
    private Double yearBackValue;
    private Double monthBackValue;
    private Double yoyChange;
    private Double momChange;
    private RegionDto region;
}
