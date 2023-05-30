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
import com.mialyk.business.services.CountyService;
import com.mialyk.business.services.MetroAreaService;
import com.mialyk.business.services.RegionService;
import com.mialyk.business.services.StateService;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "Regions", description = "Endpoints to read regions' collections of states, counties, metro areas")
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
    @Operation(summary = "Get available region types", description = "Returns collection of available region types")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<String>> getRegionTypes() { 
        List<String> regionTypes = regionService.getRegionTypes();
        return ResponseEntity.ok(regionTypes);
    }

    @GetMapping("/states")
    @Operation(summary = "Get all states", description = "Get all states. Returns collection of all states")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<StateDto>> getStates() { 
        List<StateDto> stateDtos = stateService.getStates();
        return ResponseEntity.ok(stateDtos);
    }

    @GetMapping("/counties")
    @Operation(summary = "Get all counties", description = "Get all counties. Returns collection of all counties")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public  ResponseEntity<List<CountyDto>> getCounties() { 
        List<CountyDto> countyDtos = countyService.getCounties();
        return ResponseEntity.ok(countyDtos);
    }

    @GetMapping("/{stateName}/counties")
    @Operation(summary = "Get counties by state name", description = "Returns collection of counties for the state")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "State not fount"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<CountyDto>> getCounties(@PathVariable String stateName) { 
        List<CountyDto> countyDtos = countyService.getCounties(stateName);
        return ResponseEntity.ok(countyDtos);
    }

    @GetMapping("/metros")
    @Operation(summary = "Get all metro areas", description = "Get all metro areas. Returns collection of all metro areas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<MetroAreaDto>> getMetros() { 
        List<MetroAreaDto> metroDtos = metroAreaService.getMetroAreaDtos();
        return ResponseEntity.ok(metroDtos);
    }

    @GetMapping("/{stateName}/metros")
    @Operation(summary = "Get metro areas by state name", description = "Returns collection of metro areas for the state")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "State not fount"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<MetroAreaDto>> getMetros(@PathVariable String stateName) { 
        List<MetroAreaDto> metroDtos = metroAreaService.getMetroAreaDtos(stateName);
        return ResponseEntity.ok(metroDtos);
    }
}
