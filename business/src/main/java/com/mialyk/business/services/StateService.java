package com.mialyk.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mialyk.business.dtos.StateDto;
//import com.mialyk.business.mappers.StateMapper;
import com.mialyk.persistence.entities.State;
import com.mialyk.persistence.repositories.StateRepository;

@Service
public class StateService {
    @Autowired
    StateRepository stateRepository;

    public List<StateDto> getStates() {
        List<State> states = stateRepository.findAll(Sort.by(Sort.Direction.ASC, "regionName"));
        List<StateDto> statesDto = new ArrayList<>();
        for (State state : states) {
            statesDto.add(new StateDto(state));
        }
        return statesDto;
    }

    public State getState(String regionName, String stateName, int regionId, int sizeRank) {
        State state;
        Optional <State> stateOptional = stateRepository.findByRegionId(regionId);
        if (stateOptional.isPresent()) {
            state = stateOptional.get();
        }
        else {
            state = new State();
            state.setRegionName(regionName);
            state.setStateName(stateName);
            state.setRegionId(regionId);
            state.setSizeRank(sizeRank);
        }
        return state;
    }

}
