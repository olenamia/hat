package com.mialyk.business.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.mialyk.business.dtos.CountyDto;
import com.mialyk.persistence.entities.County;
import com.mialyk.persistence.entities.State;
import com.mialyk.persistence.repositories.CountyRepository;
import com.mialyk.persistence.repositories.StateRepository;

@Service
public class CountyService implements ICountyService {
    @Autowired
    private CountyRepository countyRepository;
    @Autowired
    private StateRepository stateRepository;
    
    @Override
    public List<CountyDto> getCountyDtos() {
        List<County> counties = countyRepository.findAll(Sort.by(Sort.Direction.ASC, "regionName"));
        List<CountyDto> countyDtos = new ArrayList<>();

        for (County county : counties) {
            countyDtos.add(new CountyDto(county));
        }
        return countyDtos;
    }

    @Override
    public County getCounty(String regionName, String stateName, int regionId, int sizeRank, int stateCodeFips, int metroCodeFips, String metro) {
        County county;
        Optional <County> countyOptional = countyRepository.findByRegionId(regionId);
        if (countyOptional.isPresent()) {
            county = countyOptional.get();
        }
        else {
            county = new County();
            county.setRegionName(regionName);
            county.setRegionId(regionId);
            county.setSizeRank(sizeRank);
            county.setStateCodeFips(stateCodeFips);
            county.setMetroCodeFips(metroCodeFips);

            Optional<State> state = stateRepository.findByStateName(stateName);
            if (state.isPresent()) {
                county.setState(state.get());
            }
            county.setMetroState(metro);
        }
        return county;
    }

    @Override
    public List<CountyDto> getCountyDtos(String stateName) {
        Optional<State> state = stateRepository.findByRegionName(stateName);
        List<CountyDto> countyDtos  = new ArrayList<>();
        if(state.isPresent()) {
            List<County>  counties = countyRepository.findByStateIdOrderByRegionNameAsc(state.get().getId());

            for (County county : counties) {
                countyDtos.add(new CountyDto(county));
            }
        }
        return countyDtos;
    }

    
}
