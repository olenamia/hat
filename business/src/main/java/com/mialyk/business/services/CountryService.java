package com.mialyk.business.services;

import java.util.Optional;

import com.mialyk.persistence.entities.Country;

public interface CountryService {

    Country getOrCreateCountry(String regionName, int regionId, int sizeRank);

    Optional<Country> getCountry(String regionName);
}
