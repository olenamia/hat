package com.mialyk.business.dtos;

import org.springframework.beans.factory.annotation.Autowired;

import com.mialyk.persistence.repositories.CountryRepository;

public class CountryDto extends RegionDto {
    @Autowired
    private CountryRepository countryRepository;

}
