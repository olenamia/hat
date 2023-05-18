package com.mialyk.business.dtos;

import com.mialyk.persistence.entities.MetroArea;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class MetroAreaDto extends RegionDto {
    public MetroAreaDto(MetroArea metro) {
        super(metro);
    }
}
