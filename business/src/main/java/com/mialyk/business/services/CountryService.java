package com.mialyk.business.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialyk.persistence.entities.Country;
import com.mialyk.persistence.repositories.CountryRepository;

@Service
public class CountryService {
    @Autowired
    private CountryRepository countryRepository;
    
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

    public Optional<Country> getCountry(String regionName) {
        Optional <Country> countryOptional = countryRepository.findByRegionName(regionName);
        return countryOptional;
    }
}
