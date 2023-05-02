package com.mialyk.business.dtos;

import com.mialyk.persistence.entities.MetroArea;

import lombok.Data;

@Data
public class MetroAreaDto extends RegionDto {
    public MetroAreaDto(MetroArea metro) {
        super(metro);
    }
}
