package com.mialyk.business.dtos;

import java.sql.Date;

import com.mialyk.persistence.entities.RegionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeValueDto {

    private Integer id;
    private Date date;
    private Double homeValue;
    private RegionType regionType;
    private Integer regionId;
}
