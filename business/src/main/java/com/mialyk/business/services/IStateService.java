package com.mialyk.business.services;

import java.util.List;

import com.mialyk.business.dtos.StateDto;
import com.mialyk.persistence.entities.State;

public interface IStateService {

    List<StateDto> getStateDtos();

    State getOrCreateState(String regionName, String stateName, int regionId, int sizeRank);

    StateDto getState(Integer id);

    StateDto createState(StateDto stateDto);

    StateDto updateState(Integer id, StateDto stateDto);

    void deleteState(Integer id);
}