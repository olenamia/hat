package com.mialyk.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mialyk.persistence.entities.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {

    Optional<Country> findByRegionId(Integer regionId);

    Optional<Country> findById(Integer id);
    
    Optional<Country> findByRegionName(String regionName);

    List<Country> findAll();
}