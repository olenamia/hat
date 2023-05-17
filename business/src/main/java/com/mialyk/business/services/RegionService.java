package com.mialyk.business.services;

import java.util.List;

import com.mialyk.persistence.entities.Region;

public interface RegionService {

    List<String> getRegionTypes();

    Boolean isRegionNew(Region region);
}
