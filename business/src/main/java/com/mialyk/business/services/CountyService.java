package com.mialyk.business.services;

import java.util.List;

import com.mialyk.business.dtos.CountyDto;
import com.mialyk.persistence.entities.County;

public interface CountyService {

    List<CountyDto> getCounties();

    List<CountyDto> getCounties(String stateName);

    County getOrCreateCounty(String regionName, String stateName, int regionId, int sizeRank, int stateCodeFips, int metroCodeFips, String metro);

    CountyDto getCounty(Integer id);

    CountyDto createCounty(CountyDto countyDto);

    CountyDto updateCounty(Integer id, CountyDto countyDto);

    void deleteCounty(Integer id);
}
