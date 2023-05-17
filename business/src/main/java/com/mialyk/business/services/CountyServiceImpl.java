package com.mialyk.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mialyk.business.dtos.CountyDto;
import com.mialyk.persistence.entities.County;
import com.mialyk.persistence.entities.State;
import com.mialyk.persistence.repositories.CountyRepository;
import com.mialyk.persistence.repositories.StateRepository;

@Service
public class CountyServiceImpl implements CountyService {
    @Autowired
    private CountyRepository countyRepository;
    @Autowired
    private StateRepository stateRepository;

    @Transactional
    @Override
    public List<CountyDto> getCountyDtos() {
        List<County> counties = countyRepository.findAll(Sort.by(Sort.Direction.ASC, "regionName"));
        List<CountyDto> countyDtos = new ArrayList<>();

        for (County county : counties) {
            countyDtos.add(new CountyDto(county));
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
                throw new IllegalArgumentException("State " + stateName + " not found");
            }
            county.setState(state.get());
            county.setMetroState(metro);
            return countyRepository.save(county);
        }
    }

    @Override
    public List<CountyDto> getCountyDtos(String stateName) {
        Optional<State> state = stateRepository.findByRegionName(stateName);

        if (!state.isPresent()) {
            throw new IllegalArgumentException("State " + stateName + " not found");
        }

        List<County>  counties = countyRepository.findByStateIdOrderByRegionNameAsc(state.get().getId());
        List<CountyDto> countyDtos  = new ArrayList<>();

        for (County county : counties) {
            countyDtos.add(new CountyDto(county));
        }

        return countyDtos;
    }
}
