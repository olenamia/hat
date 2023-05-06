package com.mialyk.business.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mialyk.persistence.entities.HomeValue;
import com.mialyk.persistence.entities.Region;

@Service
public class RegionService {

    public static List<String> getRegionTypes() {
        return Arrays.stream(HomeValue.RegionType.values()).map(regionType -> regionType.name()).toList();
    }
    public Boolean isRegionNew(Region region) {
        return region.getId() == 0;
    }
}