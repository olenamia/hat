package com.mialyk.business.services;

import com.mialyk.business.dtos.HomeValueDto;

public interface HomeValueService {

    HomeValueDto getHomeValue(Integer homeValueId);

    HomeValueDto createHomeValue(HomeValueDto homeValueDto);

    HomeValueDto updateHomeValue(Integer homeValueId, HomeValueDto homeValueDto);

    void deleteHomeValue(Integer homeValueId);
}
