package com.mialyk.business.mappers;

import org.springframework.stereotype.Component;

import com.mialyk.business.dtos.StateDto;
import com.mialyk.persistence.entities.State;

@Component
public class StateMapper {

    public State map(StateDto stateDto) {
        State state = new State();

        state.setId(stateDto.getId());
        state.setRegionName(stateDto.getName());
        state.setRegionId(stateDto.getRegionId());
        state.setStateName(stateDto.getShortName());
        state.setSizeRank(stateDto.getSizeRank());
        return state;
    }

    public StateDto map(State state) {
        StateDto stateDto = new StateDto();

        stateDto.setId(state.getId());
        stateDto.setName(state.getRegionName());
        stateDto.setRegionId(state.getRegionId());
        stateDto.setShortName(state.getStateName());
        stateDto.setSizeRank(state.getSizeRank());
        return stateDto;
    }
}
