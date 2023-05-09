package com.mialyk.business.services;

import java.util.List;

import com.mialyk.business.dtos.StateDto;
import com.mialyk.persistence.entities.State;

public interface IStateService {

    List<StateDto> getStateDtos();

    State getState(String regionName, String stateName, int regionId, int sizeRank);

}