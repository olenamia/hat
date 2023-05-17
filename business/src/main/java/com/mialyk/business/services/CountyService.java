package com.mialyk.business.services;

import java.util.List;

import com.mialyk.business.dtos.CountyDto;
import com.mialyk.persistence.entities.County;

public interface CountyService {

    List<CountyDto> getCountyDtos();

    County getOrCreateCounty(String regionName, String stateName, int regionId, int sizeRank, int stateCodeFips,
            int metroCodeFips, String metro);

    List<CountyDto> getCountyDtos(String stateName);
}
