package com.mialyk.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mialyk.persistence.entities.MetroArea;

@Repository
public interface MetroAreaRepository extends JpaRepository<MetroArea, Integer> {

    Optional<MetroArea> findByRegionId(Integer regionId);

    Optional<MetroArea> findByRegionName(String metroName);

    Optional<MetroArea> findByStateId(Integer stateId);

    List<MetroArea> findByStateIdOrderByRegionNameAsc(Integer stateId);
    
    List<MetroArea> findAll();
}