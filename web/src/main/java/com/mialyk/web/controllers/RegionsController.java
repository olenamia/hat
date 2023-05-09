package com.mialyk.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mialyk.business.dtos.CountyDto;
import com.mialyk.business.dtos.MetroAreaDto;
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
    @Autowired
    private RegionService regionService;

    @GetMapping("/types")
    public List<String> getRegionTypes() { 
        return regionService.getRegionTypes();
    }

    @GetMapping("/states")
    public List<StateDto> getStates() { 
        return stateService.getStateDtos();
    }

    @GetMapping("/metros")
    public List<MetroAreaDto> getMetros() { 
        return metroAreaService.getMetroAreaDtos();
    }

    @GetMapping("/{stateName}/metros")
    public List<MetroAreaDto> getMetros(@PathVariable String stateName) { 
        return metroAreaService.getMetroAreaDtos(stateName);
    }

    @GetMapping("/counties")
    public List<CountyDto> getCounties() { 
        return countyService.getCountyDtos();
    }

    @GetMapping("/{stateName}/counties")
    public List<CountyDto> getCounties(@PathVariable String stateName) { 
        return countyService.getCountyDtos(stateName);
    }
}
