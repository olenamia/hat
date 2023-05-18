package com.mialyk.business.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mialyk.business.dtos.StateDto;
import com.mialyk.persistence.entities.State;
import com.mialyk.persistence.repositories.StateRepository;

@Service
public class StateServiceImpl implements StateService {
    @Autowired
    StateRepository stateRepository;

    private static HashMap<String, String> shortStateNames;
    static {
        shortStateNames = new HashMap<>();
        shortStateNames.put("California", "CA");
        shortStateNames.put("Texas", "TX");
        shortStateNames.put("Florida", "FL");
        shortStateNames.put("Illinois", "IL");
        shortStateNames.put("Ohio", "OH");
        shortStateNames.put("Georgia", "GA");
        shortStateNames.put("North Carolina", "NC");
        shortStateNames.put("Michigan", "MI");
        shortStateNames.put("New Jersey", "NJ");
        shortStateNames.put("Virginia", "VA");
        shortStateNames.put("Washington", "WA");
        shortStateNames.put("Arizona", "AZ");
        shortStateNames.put("Massachusetts", "MA");
        shortStateNames.put("Tennessee", "TN");
        shortStateNames.put("Indiana", "IN");
        shortStateNames.put("Missouri", "MO");
        shortStateNames.put("Maryland", "MD");
        shortStateNames.put("Wisconsin", "WI");
        shortStateNames.put("Colorado", "CO");
        shortStateNames.put("Minnesota", "MN");
        shortStateNames.put("South Carolina", "SC");
        shortStateNames.put("Alabama", "AL");
        shortStateNames.put("Louisiana", "LA");
        shortStateNames.put("Kentucky", "KY");
        shortStateNames.put("Oregon", "OR");
        shortStateNames.put("Oklahoma", "OK");
        shortStateNames.put("Connecticut", "CT");
        shortStateNames.put("Iowa", "IA");
        shortStateNames.put("Utah", "UT");
        shortStateNames.put("Arkansas", "AR");
        shortStateNames.put("Mississippi", "MS");
        shortStateNames.put("Nevada", "NV");
        shortStateNames.put("Kansas", "KS");
        shortStateNames.put("Nebraska", "NE");
        shortStateNames.put("West Virginia", "WV");
        shortStateNames.put("Idaho", "ID");
        shortStateNames.put("Hawaii", "HI");
        shortStateNames.put("New Hampshire", "NH");
        shortStateNames.put("Maine", "ME");
        shortStateNames.put("Rhode Island", "RI");
        shortStateNames.put("Delaware", "DE");
        shortStateNames.put("South Dakota", "SD");
        shortStateNames.put("District of Columbia", "DC");
        shortStateNames.put("Vermont", "VT");
        shortStateNames.put("New York", "NY");
        shortStateNames.put("Pennsylvania", "PA");
        shortStateNames.put("New Mexico", "NM");
        shortStateNames.put("Montana", "MT");
        shortStateNames.put("North Dakota", "ND");
        shortStateNames.put("Alaska", "AK");
        shortStateNames.put("Wyoming", "WY");
    }

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
            state.setStateName(stateName != null && stateName.length() > 0 ? stateName : shortStateNames.get(regionName));
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
            throw new IllegalArgumentException("State with ID " + id + " not found");
        }

        return new StateDto(state.get());
    }

    @Transactional
    @Override
    public StateDto updateState(Integer id, StateDto stateDto) {
        Optional <State> stateOptional = stateRepository.findById(id);

        if (!stateOptional.isPresent()) {
            throw new IllegalArgumentException("State with ID " + id + " not found");
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
            throw new IllegalArgumentException("State with ID " + id + " not found");
        }
        stateRepository.deleteById(id);
    }
}
