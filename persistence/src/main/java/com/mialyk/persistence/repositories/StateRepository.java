package com.mialyk.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.mialyk.persistence.entities.State;

@Repository
public interface StateRepository extends JpaRepository<State, Integer> {

    Optional<State> findById(Integer id);

    Optional<State> findByRegionName(String regionName);

    Optional<State> findByStateName(String userName);

    Optional<State> findByRegionId(Integer regionId);

    List<State> findAll();

    void deleteById(Integer id);
}