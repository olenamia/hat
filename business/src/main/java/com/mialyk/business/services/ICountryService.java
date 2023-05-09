package com.mialyk.business.services;

import java.util.Optional;

import com.mialyk.persistence.entities.Country;

public interface ICountryService {

    Country getCountry(String regionName, int regionId, int sizeRank);

    Optional<Country> getCountry(String regionName);

}