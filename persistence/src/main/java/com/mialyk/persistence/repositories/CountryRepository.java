package com.mialyk.persistence.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mialyk.persistence.entities.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

    Optional<Country> findByRegionId(Integer regionId);
}