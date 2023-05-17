package com.mialyk.business.zillow.services;

import java.util.List;

import com.mialyk.business.zillow.dtos.ZillowHomeValueDto;

public interface ZillowDataStorageService {

    void saveHomeValues(List<ZillowHomeValueDto> zillowHomeValueDtos);
}
