package com.mialyk.business.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialyk.persistence.entities.Country;
import com.mialyk.persistence.entities.County;
import com.mialyk.persistence.entities.MetroArea;
import com.mialyk.persistence.entities.Region;
import com.mialyk.persistence.entities.RegionType;
import com.mialyk.persistence.entities.State;
import com.mialyk.persistence.repositories.CountryRepository;
import com.mialyk.persistence.repositories.CountyRepository;
import com.mialyk.persistence.repositories.MetroAreaRepository;
import com.mialyk.persistence.repositories.StateRepository;

@Service
public class RegionServiceImpl implements RegionService {
    @Autowired
    StateRepository stateRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    CountyRepository countyRepository;
    @Autowired
    MetroAreaRepository metroRepository;

    @Override
    public List<String> getRegionTypes() {
        return Arrays.stream(RegionType.values()).map(regionType -> regionType.name()).toList();
    }
    @Override
    public Boolean isRegionNew(Region region) {
        return region.getId() == 0;
    }
    @Override
    public Region getRegion(Integer regionId,  RegionType regionType) {
        if (regionType == RegionType.STATE) {
            Optional<State> stateOptional = stateRepository.findById(regionId);
            if (!stateOptional.isPresent()) {
                throw new IllegalArgumentException("State with ID " + regionId + " not found");
            }
            return stateOptional.get();
        }
        else if (regionType == RegionType.COUNTY) {
            Optional<County> countyOptional = countyRepository.findById(regionId);
            if (!countyOptional.isPresent()) {
                throw new IllegalArgumentException("County with ID " + regionId + " not found");
            }
            return countyOptional.get();
        }
        else if (regionType == RegionType.METRO) {
            Optional<MetroArea> metroOptional = metroRepository.findById(regionId);
            if (!metroOptional.isPresent()) {
                throw new IllegalArgumentException("Metro with ID " + regionId + " not found");
            }
            return metroOptional.get();
        }
        else if (regionType == RegionType.COUNTRY) {
            Optional<Country> countryOptional = countryRepository.findById(regionId);
            if (!countryOptional.isPresent()) {
                throw new IllegalArgumentException("Country with ID " + regionId + " not found");
            }
            return countryOptional.get();
        }
        else {
            throw new IllegalArgumentException("Illegal region type " + regionType + " not found");
        }
    }
}
