package com.mialyk.business.services;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mialyk.persistence.entities.Region;
import com.mialyk.persistence.entities.RegionType;

@Service
public class RegionServiceImpl implements RegionService {

    @Override
    public List<String> getRegionTypes() {
        return Arrays.stream(RegionType.values()).map(regionType -> regionType.name()).toList();
    }
    @Override
    public Boolean isRegionNew(Region region) {
        return region.getId() == 0;
    }
}
