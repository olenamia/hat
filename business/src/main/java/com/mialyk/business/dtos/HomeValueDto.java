package com.mialyk.business.dtos;

import java.io.Serializable;
import java.sql.Date;
import java.text.ParseException;
import java.util.Optional;

import org.postgresql.util.PGobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mialyk.persistence.entities.HomeValue;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class HomeValueDto implements Serializable {
    private int id;
    private Date date;
    private Double homeValue;
    private Double yoyChange;
    //private Optional<Double> momChange;

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
    }

    public HomeValueDto(HomeValue homeValue, Double yoyChange) {
        this(homeValue);
        this.yoyChange = yoyChange;
    }

    public HomeValueDto(PGobject pgObject, Double yoyChange) throws ParseException {

        String tupleString = pgObject.getValue();
        String[] fields = tupleString.substring(1, tupleString.length() - 1).split(",");

        this.id = Integer.parseInt(fields[0]);
        this.date = Date.valueOf(fields[1]);
        this.homeValue = Double.parseDouble(fields[3]);
        this.yoyChange = yoyChange;
    }
}
