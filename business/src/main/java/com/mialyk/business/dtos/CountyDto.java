package com.mialyk.business.dtos;

import com.mialyk.persistence.entities.County;

public class CountyDto extends RegionDto {
    public CountyDto(County county) {
        super(county);
    }
}
