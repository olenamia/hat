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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Historical Data", description = "Endpoints to get changes in home value within a period")
@RestController
@RequestMapping("hat/api/historical")
public class HistoricalController {
    @Autowired
    private IHomeValueService homeValueZillowService;

    @GetMapping({"/values/state/{stateName}"})
    @Operation(summary = "Historical data for the state", description = "Returns home values and year-over-year changes for the state for more than 20 years")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
    })
    public List<HomeValueDto> getHomeValuesByState(@PathVariable String stateName) { 
        return homeValueZillowService.getHomeValuesByState(stateName);
    }

    @GetMapping({"/values/US"})
    @Operation(summary = "Historical data for the US", description = "Returns home values and year-over-year changes for the US for more than 20 years")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
    })
    public List<HomeValueDto> getHomeValuesUS() { 
        return homeValueZillowService.getHistoricalDataUS("United States");
    }

    @GetMapping({"/values/metro/{stateName}/{metroRegionId}"})
    @Operation(summary = "Historical data for the metro area", description = "Returns home values and year-over-year changes for the metro area for more than 20 years")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
    })
    public List<HomeValueDto> getHomeValuesByMetro(@PathVariable String stateName, @PathVariable Integer metroRegionId) { 
        return homeValueZillowService.getHistoricalDataByRegionIdAndRegioType(metroRegionId, RegionType.METRO);
    }

    @GetMapping({"/values/county/{stateName}/{countyRegionId}"})
    @Operation(summary = "Historical data for the county", description = "Returns home values and year-over-year changes for the county for more than 20 years")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
    })
    public List<HomeValueDto> getHomeValuesByCounty(@PathVariable String stateName, @PathVariable Integer countyRegionId) { 
        return homeValueZillowService.getHistoricalDataByRegionIdAndRegioType(countyRegionId, RegionType.COUNTY);
    }
}
