package com.mialyk.business.dtos;

import com.mialyk.persistence.entities.County;

import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
public class CountyDto extends RegionDto {
    public CountyDto(County county) {
        super(county);
    }
}
