package com.mialyk.business.zillow.services;

import org.springframework.stereotype.Component;

import com.mialyk.persistence.entities.RegionType;

@Component
public class ZillowDataMapper {
    public RegionType getRegionType(String csvRegionType) {
        RegionType regionType = null;
        switch(csvRegionType) {
            case "state":
                regionType = RegionType.STATE;
                break;
            case "county":
                regionType = RegionType.COUNTY;
                break;
            case "country":
                regionType = RegionType.COUNTRY;
                break;
            case "msa":
                regionType = RegionType.METRO;
                break;
        }
        return regionType;
    }
}
