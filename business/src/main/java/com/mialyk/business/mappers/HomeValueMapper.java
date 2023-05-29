package com.mialyk.business.mappers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mialyk.business.common.Utils;
import com.mialyk.business.dtos.HomeValueDto;
import com.mialyk.business.services.RegionService;
import com.mialyk.persistence.entities.HomeValue;
import com.mialyk.persistence.entities.RegionType;

@Component
public class HomeValueMapper {
    @Autowired
    RegionService regionService;

    public HomeValue map(HomeValueDto homeValueDto) {
        HomeValue homeValue = new HomeValue();
        homeValue.setId(homeValueDto.getId());
        homeValue.setValue(new BigDecimal(homeValueDto.getHomeValue()));
        homeValue.setDate(homeValueDto.getDate());
        homeValue.setRegionType(homeValueDto.getRegionType());
        homeValue.setRegion(regionService.getRegion(homeValueDto.getRegionId(),  homeValueDto.getRegionType()));
        return homeValue;
    }

    public HomeValueDto map(HomeValue homeValue) {
        HomeValueDto homeValueDto = new HomeValueDto();
        homeValueDto.setId(homeValue.getId());
        homeValueDto.setHomeValue(Utils.roundDouble(homeValue.getValue()));
        homeValueDto.setDate(homeValue.getDate());
        homeValueDto.setRegionType(homeValue.getRegionType());

        if (homeValue.getRegionType() == RegionType.STATE) {
            homeValueDto.setRegionId(homeValue.getStateValue().getId());
        }
        else if (homeValue.getRegionType() == RegionType.COUNTY) {
            homeValueDto.setRegionId(homeValue.getCountyValue().getId());
        }
        else if (homeValue.getRegionType() == RegionType.METRO) {
            homeValueDto.setRegionId(homeValue.getMetroValue().getId());
        }
        else if (homeValue.getRegionType() == RegionType.COUNTRY) {
            homeValueDto.setRegionId(homeValue.getCountryValue().getId());
        }

        return homeValueDto;
    }
}
