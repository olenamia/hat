package com.mialyk.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mialyk.business.dtos.CountyDto;
import com.mialyk.business.mappers.CountyMapper;
import com.mialyk.persistence.entities.County;
import com.mialyk.persistence.entities.State;
import com.mialyk.persistence.repositories.CountyRepository;
import com.mialyk.persistence.repositories.StateRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CountyServiceImpl implements CountyService {
    @Autowired
    private CountyRepository countyRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CountyMapper countyDtoMapper;

    @Override
    public List<CountyDto> getCounties() {
        List<County> counties = countyRepository.findAll(Sort.by(Sort.Direction.ASC, "regionName"));
        List<CountyDto> countyDtos = new ArrayList<>();

        for (County county : counties) {
            countyDtos.add(countyDtoMapper.map(county));
        }
        return countyDtos;
    }

    @Transactional
    @Override
    public County getOrCreateCounty(String regionName, String stateName, int regionId, int sizeRank, int stateCodeFips, int metroCodeFips, String metro) {
        County county;
        Optional <County> countyOptional = countyRepository.findByRegionId(regionId);
        if (countyOptional.isPresent()) {
            return countyOptional.get();
        }
        else {
            county = new County();
            county.setRegionName(regionName);
            county.setRegionId(regionId);
            county.setSizeRank(sizeRank);
            county.setStateCodeFips(stateCodeFips);
            county.setMetroCodeFips(metroCodeFips);

            Optional<State> state = stateRepository.findByStateName(stateName);
            if (!state.isPresent()) {
                throw new EntityNotFoundException("State " + stateName + " not found");
            }
            county.setState(state.get());
            county.setMetroState(metro);
            return countyRepository.save(county);
        }
    }

    @Override
    public List<CountyDto> getCounties(String stateName) {
        Optional<State> state = stateRepository.findByRegionName(stateName);

        if (!state.isPresent()) {
            throw new EntityNotFoundException("State " + stateName + " not found");
        }

        List<County>  counties = countyRepository.findByStateIdOrderByRegionNameAsc(state.get().getId());
        List<CountyDto> countyDtos  = new ArrayList<>();

        for (County county : counties) {
            countyDtos.add(countyDtoMapper.map(county));
        }
        return countyDtos;
    }

    @Override
    public CountyDto getCounty(Integer id) {

        Optional<County> county = countyRepository.findById(id);

        if (!county.isPresent()) {
            throw new EntityNotFoundException("County with ID " + id + " not found");
        }
        return countyDtoMapper.map(county.get());
     }

    @Transactional
    @Override
    public CountyDto createCounty(CountyDto countyDto) {
        County county = countyDtoMapper.map(countyDto);
        return countyDtoMapper.map(countyRepository.save(county));
     }

    @Transactional
    @Override
    public CountyDto updateCounty(Integer id, CountyDto countyDto) {
        Optional<County> countyOptional = countyRepository.findById(id);

        if (!countyOptional.isPresent()) {
            throw new EntityNotFoundException("County with ID " + id + " not found");
        }
        County county = countyOptional.get();
        county.setRegionName(countyDto.getName());
        county.setRegionId(countyDto.getRegionId());
        county.setSizeRank(countyDto.getSizeRank());
        county.setStateCodeFips(countyDto.getStateCodeFips());
        county.setMetroCodeFips(countyDto.getMetroCodeFips());
        county.setMetroState(countyDto.getMetroState());

        Integer newStateId = countyDto.getState().getId();
        Optional<State> stateOptional = stateRepository.findById(newStateId);

        if (!stateOptional.isPresent()) {
            throw new EntityNotFoundException("State with ID " + id + " not found");
        }
        county.setState(stateOptional.get());
        return countyDtoMapper.map(countyRepository.save(county));
     }

    @Transactional
    @Override
    public void deleteCounty(Integer id) {
        Optional<County> county = countyRepository.findById(id);

        if (!county.isPresent()) {
            throw new EntityNotFoundException("County with ID " + id + " not found");
        }
        countyRepository.deleteById(id);
    }
}
