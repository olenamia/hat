package com.mialyk.business.services;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mialyk.business.dtos.HomeValueDto;
import com.mialyk.business.mappers.HomeValueMapper;
import com.mialyk.persistence.entities.HomeValue;
import com.mialyk.persistence.repositories.HomeValueRepository;

@Service
public class HomeValueServiceImpl implements HomeValueService {
    @Autowired
    HomeValueRepository homeValueRepository;
    @Autowired
    HomeValueMapper homeValueDtoMapper;
    @Autowired
    RegionService regionService;

    @Override
    public HomeValueDto getHomeValue(Integer homeValueId) {
        Optional<HomeValue> homeValue = homeValueRepository.findById(homeValueId);

        if (!homeValue.isPresent()) {
            throw new IllegalArgumentException("Home Value with ID " + homeValueId + " not found");
        }
        return homeValueDtoMapper.map(homeValue.get());
    }

    @Transactional
    @Override
    public HomeValueDto createHomeValue(HomeValueDto homeValueDto) {
        HomeValue homeValue = homeValueDtoMapper.map(homeValueDto);
        return homeValueDtoMapper.map(homeValueRepository.save(homeValue));
    }

    @Transactional
    @Override
    public HomeValueDto updateHomeValue(Integer homeValueId, HomeValueDto homeValueDto) {
        Optional<HomeValue> homeValueOptional = homeValueRepository.findById(homeValueId);

        if (!homeValueOptional.isPresent()) {
            throw new IllegalArgumentException("Home value with ID " + homeValueId + " not found");
        }
        HomeValue homeValue = homeValueOptional.get();
        homeValue.setValue(new BigDecimal(homeValueDto.getHomeValue()));
        homeValue.setDate(homeValueDto.getDate());
        homeValue.setRegionType(homeValueDto.getRegionType());
        homeValue.setRegion(regionService.getRegion(homeValueDto.getRegionId(),  homeValueDto.getRegionType()));
        return homeValueDtoMapper.map(homeValueRepository.save(homeValue));
    }

    @Transactional
    @Override
    public void deleteHomeValue(Integer homeValueId) {
        Optional<HomeValue> homeValue = homeValueRepository.findById(homeValueId);

        if (!homeValue.isPresent()) {
            throw new IllegalArgumentException("Home Value with ID " + homeValueId + " not found");
        }
        homeValueRepository.deleteById(homeValueId);
    }
}
