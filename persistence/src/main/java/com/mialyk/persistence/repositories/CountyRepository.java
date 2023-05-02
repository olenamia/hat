package com.mialyk.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mialyk.persistence.entities.County;

@Repository
public interface CountyRepository extends JpaRepository<County, Long> {

    Optional<County> findByRegionNameAndStateId(String username, Integer stateid);

    List<County> findByStateId(String username);

    Optional<County> findByRegionId(Integer regionId);


    List<County> findAll();
}