package com.mialyk.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mialyk.persistence.entities.County;

public interface CountyRepository extends JpaRepository<County, Integer> {

    Optional<County> findByRegionNameAndStateId(String username, Integer stateid);

    List<County> findByStateId(String username);

    Optional<County> findByRegionId(Integer regionId);
    
    List<County> findByStateIdOrderByRegionNameAsc(Integer stateId);

    List<County> findAll();
}