package com.mialyk.business.zillow.services;

import java.util.List;

import com.mialyk.business.zillow.model.ZillowHomeValue;

public interface ZillowDataStorageService {

    void saveHomeValues(List<ZillowHomeValue> zillowHomeValueDtos);
}
