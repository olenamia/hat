package com.mialyk.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mialyk.business.dtos.StateDto;
import com.mialyk.persistence.entities.State;
import com.mialyk.persistence.repositories.StateRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class StateService implements IStateService {
    @Autowired
    StateRepository stateRepository;

    @Override
    public List<StateDto> getStateDtos() {
        List<State> states = stateRepository.findAll(Sort.by(Sort.Direction.ASC, "regionName"));
        List<StateDto> statesDto = new ArrayList<>();
        for (State state : states) {
            statesDto.add(new StateDto(state));
        }
        return statesDto;
    }

    @Transactional
    @Override
    public State getOrCreateState(String regionName, String stateName, int regionId, int sizeRank) {
        State state;
        Optional <State> stateOptional = stateRepository.findByRegionId(regionId);
        if (stateOptional.isPresent()) {
            return stateOptional.get();
        }
        else {
            state = new State();
            state.setRegionName(regionName);
            state.setStateName(stateName);
            state.setRegionId(regionId);
            state.setSizeRank(sizeRank);
            return stateRepository.save(state);
        }
    }

    @Transactional
    @Override
    public StateDto createState(StateDto stateDto) {
        State state = new State();
        state.setRegionName(stateDto.getName());
        state.setStateName(stateDto.getShortName());
        state.setRegionId(stateDto.getRegionId());
        state.setSizeRank(stateDto.getSizeRank());
        return new StateDto(stateRepository.save(state));
    }

    @Override
    public StateDto getState(Integer id) {
        Optional <State> state = stateRepository.findById(id);

        if (!state.isPresent()) {
            throw new EntityNotFoundException("State with ID " + id + " not found");
        }

        return new StateDto(state.get());
    }

    @Transactional
    @Override
    public StateDto updateState(Integer id, StateDto stateDto) {
        Optional <State> stateOptional = stateRepository.findById(id);

        if (!stateOptional.isPresent()) {
            throw new EntityNotFoundException("State with ID " + id + " not found");
        }
        State state = stateOptional.get();
        state.setRegionName(stateDto.getName());
        state.setStateName(stateDto.getShortName());
        state.setRegionId(stateDto.getRegionId());
        state.setSizeRank(stateDto.getSizeRank());
        return new StateDto(stateRepository.save(state));
    }

    @Transactional
    @Override
    public void deleteState(Integer id) {

        Optional <State> stateOptional = stateRepository.findById(id);

        if (!stateOptional.isPresent()) {
            throw new EntityNotFoundException("State with ID " + id + " not found");
        }
        stateRepository.deleteById(id);
    }
}
