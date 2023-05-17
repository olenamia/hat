package com.mialyk.business.services;

import java.util.List;
import java.util.Optional;

import com.mialyk.business.dtos.MetroAreaDto;
import com.mialyk.persistence.entities.MetroArea;

public interface MetroAreaService {

    MetroArea getOrCreateMetroArea(String regionName, String stateName, int regionId, int sizeRank);

    List<MetroAreaDto> getMetroAreaDtos();

    List<MetroAreaDto> getMetroAreaDtos(String stateName);

    Optional<MetroArea> getMetroArea(Integer regionId);
}
