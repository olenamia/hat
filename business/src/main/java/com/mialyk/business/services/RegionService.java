package com.mialyk.business.services;

import java.util.List;

import com.mialyk.persistence.entities.Region;
import com.mialyk.persistence.entities.RegionType;

public interface RegionService {
    List<String> getRegionTypes();
    Boolean isRegionNew(Region region);
    Region getRegion(Integer id,  RegionType regionType);
}
