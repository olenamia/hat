package com.mialyk.business.services;
import java.sql.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialyk.persistence.entities.Country;
import com.mialyk.persistence.entities.County;
import com.mialyk.persistence.entities.MetroArea;
import com.mialyk.persistence.entities.Region;
import com.mialyk.persistence.entities.State;
import com.mialyk.persistence.entities.HomeValue.RegionType;
import com.mialyk.persistence.repositories.CountryRepository;
import com.mialyk.persistence.repositories.CountyRepository;
import com.mialyk.persistence.repositories.MetroAreaRepository;
import com.mialyk.persistence.repositories.StateRepository;

@Service
public class RegionService {
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private StateRepository stateRepository;
    @Autowired
    private CountyRepository countyRepository;
    @Autowired
    private MetroAreaRepository metroAreaRepository;
    //private HomeValueZillowRepository homeValueZillowRepository;
    /* 
    public Region getRegion(String regionType, String regionName, String stateName, int regionId, int sizeRank) {
        //if (regionType.toUpperCase() == homeValueZillowRepository.findDistinctRegionTypes().STATE.name())
        if (RegionType.STATE.name().equalsIgnoreCase(regionType))
            return getState(regionName, stateName, regionId, sizeRank);
        else if (RegionType.COUNTRY.name().equalsIgnoreCase(regionType)) {
            return getCountry(regionName, regionId, sizeRank);
        } 

        return null;
    }*/
/*
    public Boolean isRegionNew(Region region) {
        //if (regionType.toUpperCase() == homeValueZillowRepository.findDistinctRegionTypes().STATE.name())
        if (RegionType.STATE.name().equalsIgnoreCase(regionType))
            return getState(regionName, stateName, regionId, sizeRank);
        else if (RegionType.COUNTRY.name().equalsIgnoreCase(regionType)) {
            return getCountry(regionName, regionId, sizeRank);
        } 
    }*/

    public Boolean isRegionNew(Region region) {
        return region.getId() == 0;
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
    public Country getCountry(String regionName, int regionId, int sizeRank) {
        Country country;
        Optional <Country> countryOptional = countryRepository.findByRegionId(regionId);
        if (countryOptional.isPresent()) {
            country = countryOptional.get();
        }
        else {
            country = new Country();
            country.setRegionName(regionName);
            country.setRegionId(regionId);
            country.setSizeRank(sizeRank);
        }
        return country;
    }
    public MetroArea getMetroArea(String regionName, String stateName, int regionId, int sizeRank) {
        MetroArea metroArea;
        Optional <MetroArea> metroAreaOptional = metroAreaRepository.findByRegionId(regionId);
        if (metroAreaOptional.isPresent()) {
            metroArea = metroAreaOptional.get();
        }
        else {
            metroArea = new MetroArea();
            metroArea.setRegionName(regionName);
            metroArea.setRegionId(regionId);
            metroArea.setSizeRank(sizeRank);
            Optional<State> state = stateRepository.findByStateName(stateName);
            if (state.isPresent()) {
                metroArea.setState(state.get());
            }
        }
        return metroArea;
    }
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
            /*
            Optional<MetroArea> metro = metroAreaRepository.findByRegionName(metro);
            if (state.isPresent()) {
                county.setMetro(metro.get());
            }
            */
        }
        return county;
    }
}