package com.mialyk.web.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mialyk.business.dtos.HomeValueDto;
import com.mialyk.business.services.IHomeValueService;
import com.mialyk.persistence.entities.RegionType;


@RestController
@RequestMapping("hat/api/historical")
public class HistoricalController {
    @Autowired
    private IHomeValueService homeValueZillowService;

    @GetMapping({"/values/state/{stateName}"})
    public List<HomeValueDto> getHomeValuesByState(@PathVariable String stateName) { 
        return homeValueZillowService.getHomeValuesByState(stateName);
    }

    @GetMapping({"/values/US"})
    public List<HomeValueDto> getHomeValuesUS() { 
        return homeValueZillowService.getHistoricalDataUS("United States");
    }

    @GetMapping({"/values/metro/{stateName}/{metroRegionId}"})
    public List<HomeValueDto> getHomeValuesByMetro(@PathVariable String stateName, @PathVariable Integer metroRegionId) { 
        return homeValueZillowService.getHistoricalDataByRegionIdAndRegioType(metroRegionId, RegionType.METRO);
    }

    @GetMapping({"/values/county/{stateName}/{countyRegionId}"})
    public List<HomeValueDto> getHomeValuesByCounty(@PathVariable String stateName, @PathVariable Integer countyRegionId) { 
        return homeValueZillowService.getHistoricalDataByRegionIdAndRegioType(countyRegionId, RegionType.COUNTY);
    }
}
