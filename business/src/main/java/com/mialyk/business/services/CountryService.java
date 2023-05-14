package com.mialyk.business.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mialyk.persistence.entities.Country;
import com.mialyk.persistence.repositories.CountryRepository;

@Service
public class CountryService implements ICountryService {
    @Autowired
    private CountryRepository countryRepository;
    
    @Transactional
    @Override
    public Country getOrCreateCountry(String regionName, int regionId, int sizeRank) {
        Country country;
        Optional <Country> countryOptional = countryRepository.findByRegionId(regionId);
        if (countryOptional.isPresent()) {
            return countryOptional.get();
        }
        else {
            country = new Country();
            country.setRegionName(regionName);
            country.setRegionId(regionId);
            country.setSizeRank(sizeRank);
            return countryRepository.save(country);
        }
    }

    @Override
    public Optional<Country> getCountry(String regionName) {
        Optional <Country> countryOptional = countryRepository.findByRegionName(regionName);
        return countryOptional;
    }
}
