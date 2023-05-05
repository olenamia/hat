package com.mialyk.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mialyk.business.dtos.CountyDto;
import com.mialyk.business.dtos.MetroAreaDto;
import com.mialyk.business.dtos.RegionDto;
import com.mialyk.business.dtos.StateDto;
import com.mialyk.business.services.CountyService;
import com.mialyk.business.services.MetroAreaService;
import com.mialyk.business.services.RegionService;
import com.mialyk.business.services.StateService;

@RestController
@RequestMapping("hat/api/regions")
public class RegionsController {
    @Autowired
    private StateService stateService;
    @Autowired
    private MetroAreaService metroAreaService;
    @Autowired
    private CountyService countyService;

    @GetMapping("/types")
    public List<String> getRegionTypes() throws JsonProcessingException, JsonMappingException {
        return RegionService.getRegionTypes();
    }

    @GetMapping("/states")
    public List<StateDto> getStates() throws JsonProcessingException, JsonMappingException {
        return stateService.getStateDtos();
    }

    @GetMapping("/metros")
    public List<MetroAreaDto> getMetros() throws JsonProcessingException, JsonMappingException {
        return metroAreaService.getMetroAreaDtos();
    }

    @GetMapping("/{stateName}/metros")
    public List<MetroAreaDto> getMetros(@PathVariable String stateName) throws JsonProcessingException, JsonMappingException {
        return metroAreaService.getMetroAreaDtos(stateName);
    }

    @GetMapping("/counties")
    public List<CountyDto> getCounties() throws JsonProcessingException, JsonMappingException {
        return countyService.getCountyDtos();
    }

    @GetMapping("/{stateName}/counties")
    public List<CountyDto> getCounties(@PathVariable String stateName) throws JsonProcessingException, JsonMappingException {
        return countyService.getCountyDtos(stateName);
    }
}
