package com.mialyk.business.dtos;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.postgresql.util.PGobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mialyk.persistence.entities.HomeValue;
import com.mialyk.persistence.entities.HomeValue.RegionType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class HomeValueDto implements Serializable {
    private int id;
    private Date date;
    private Double homeValue;
    private String regionType;
    private Double yoyChange;
    private Optional<Double> momChange;
    private RegionDto region;

    public HomeValueDto(HomeValue homeValue) {
        if (homeValue.getId() != 0) {
            this.id = homeValue.getId();
        }

        if (homeValue.getDate() != null) {
            this.date = homeValue.getDate();
        }

        if (homeValue.getValue() != null) {
            this.homeValue = homeValue.getValue().doubleValue();
        }

        if (homeValue.getRegionType() != null) {
            if (homeValue.getRegionType() == RegionType.STATE) {
                this.regionType = homeValue.getRegionType().name();
                this.region = new RegionDto(homeValue.getStateValue());
            }
        }
    }

    public HomeValueDto(HomeValue homeValue, Double yoyChange) {
        this(homeValue);
        this.yoyChange = yoyChange;
    }

    public HomeValueDto(PGobject pgObject, Double yoyChange, String regionName) throws ParseException {

        String tupleString = pgObject.getValue();
        String[] fields = tupleString.substring(1, tupleString.length() - 1).split(",");

        this.id = Integer.parseInt(fields[0]);
        //this.date = new SimpleDateFormat("yyyy-MM-dd").parse(fields[1]);
        this.date = Date.valueOf(fields[1]);
        this.regionType = fields[2];
        this.homeValue = Double.parseDouble(fields[3]);
        this.region = new RegionDto(regionName);
        this.yoyChange = yoyChange;
    }


}
