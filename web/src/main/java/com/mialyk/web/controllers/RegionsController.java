package com.mialyk.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mialyk.business.dtos.CountyDto;
import com.mialyk.business.dtos.MetroAreaDto;
import com.mialyk.business.dtos.StateDto;
import com.mialyk.business.services.ICountyService;
import com.mialyk.business.services.IMetroAreaService;
import com.mialyk.business.services.IRegionService;
import com.mialyk.business.services.IStateService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "Regions", description = "Endpoints to read regions' collections of states, counties, metro areas")
@RestController
@RequestMapping("hat/api/regions")
public class RegionsController {

    @Autowired
    private IStateService stateService;
    @Autowired
    private IMetroAreaService metroAreaService;
    @Autowired
    private ICountyService countyService;
    @Autowired
    private IRegionService regionService;

    @GetMapping("/types")
    @Operation(summary = "Get available region types", description = "Returns collection of available region types")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<String>> getRegionTypes() { 
        List<String> regionTypes = regionService.getRegionTypes();
        return ResponseEntity.ok(regionTypes);
    }

    @GetMapping("/states")
    @Operation(summary = "Get all states", description = "Get all states. Returns collection of all states")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<StateDto>> getStates() { 
        List<StateDto> stateDtos = stateService.getStateDtos();
        return ResponseEntity.ok(stateDtos);
    }

    @GetMapping("/counties")
    @Operation(summary = "Get all counties", description = "Get all counties. Returns collection of all counties")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK")
    })
    public  ResponseEntity<List<CountyDto>> getCounties() { 
        List<CountyDto> countyDtos = countyService.getCountyDtos();
        return ResponseEntity.ok(countyDtos);
    }

    @GetMapping("/{stateName}/counties")
    @Operation(summary = "Get counties by state name", description = "Returns collection of counties for the state")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "State not fount")
    })
    public List<CountyDto> getCounties(@PathVariable String stateName) { 
        return countyService.getCountyDtos(stateName);
    }

    @GetMapping("/metros")
    @Operation(summary = "Get all metro areas", description = "Get all metro areas. Returns collection of all metro areas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<MetroAreaDto>> getMetros() { 
        List<MetroAreaDto> metroDtos = metroAreaService.getMetroAreaDtos();
        return ResponseEntity.ok(metroDtos);
    }

    @GetMapping("/{stateName}/metros")
    @Operation(summary = "Get metro areas by state name", description = "Returns collection of metro areas for the state")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "State not fount")
    })
    public List<MetroAreaDto> getMetros(@PathVariable String stateName) { 
        return metroAreaService.getMetroAreaDtos(stateName);
    }
}
