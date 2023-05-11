package com.mialyk.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mialyk.business.dtos.CountyDto;
import com.mialyk.business.dtos.MetroAreaDto;
import com.mialyk.business.dtos.StateDto;
import com.mialyk.business.services.ICountyService;
import com.mialyk.business.services.IMetroAreaService;
import com.mialyk.business.services.IRegionService;
import com.mialyk.business.services.IStateService;
import com.mialyk.web.security.ApiKeyValidator;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

//@Tag(name = "Regions", description = "Endpoints for managing regions: states, counties and metro areas")
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
    @Autowired
    private ApiKeyValidator apiKeyValidator;

    /**
     * Read collections of Regions of different types endpoints
     */
    @Tag(name = "Regions", description = "Endpoints to read regions' collections of states, counties, metro areas")
    @GetMapping("/types")
    @Operation(summary = "Get available region types", description = "Returns collection of available region types")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<String>> getRegionTypes() { 
        List<String> regionTypes = regionService.getRegionTypes();
        return ResponseEntity.ok(regionTypes);
    }
    @Tag(name = "Regions")
    @GetMapping("/states")
    @Operation(summary = "Get all states", description = "Get all states. Returns collection of all states")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<StateDto>> getStates() { 
        List<StateDto> stateDtos = stateService.getStateDtos();
        return ResponseEntity.ok(stateDtos);
    }
    @Tag(name = "Regions")
    @GetMapping("/counties")
    @Operation(summary = "Get all counties", description = "Get all counties. Returns collection of all counties")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK")
    })
    public  ResponseEntity<List<CountyDto>> getCounties() { 
        List<CountyDto> countyDtos = countyService.getCountyDtos();
        return ResponseEntity.ok(countyDtos);
    }
    @Tag(name = "Regions")
    @GetMapping("/{stateName}/counties")
    public List<CountyDto> getCounties(@PathVariable String stateName) { 
        return countyService.getCountyDtos(stateName);
    }
    @Tag(name = "Regions")
    @GetMapping("/metros")
    @Operation(summary = "Get all metro areas", description = "Get all metro areas. Returns collection of all metro areas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK")
    })
    public ResponseEntity<List<MetroAreaDto>> getMetros() { 
        List<MetroAreaDto> metroDtos = metroAreaService.getMetroAreaDtos();
        return ResponseEntity.ok(metroDtos);
    }
    @Tag(name = "Regions")
    @GetMapping("/{stateName}/metros")
    public List<MetroAreaDto> getMetros(@PathVariable String stateName) { 
        return metroAreaService.getMetroAreaDtos(stateName);
    }

    /**
     * State CRUD endpoints
     */
    @Tag(name = "State", description = "Endpoints for managing states")
    @GetMapping("/state/{stateId}")
    @Operation(summary = "Get a state by ID", description = "Get a single state by it's ID. Returns a state")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "State successfully rerurned"),
        @ApiResponse(responseCode = "404", description = "State not found")
    })
    public ResponseEntity<StateDto> getState(@PathVariable Integer stateId) { 
        StateDto stateDto =  stateService.getState(stateId);
        return ResponseEntity.ok(stateDto);
    }
    @Tag(name = "State")
    @Operation(summary = "Create a state", description = "Create a single state. Returns created state")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "State successfully created"),
        @ApiResponse(responseCode =  "401", description = "Unauthorized")
    })
    @PostMapping("/state")
    public ResponseEntity<StateDto> createState(@RequestBody StateDto stateDto, @RequestHeader("api-key") String apiKey) { 
        apiKeyValidator.validate(apiKey);
        StateDto createdStateDto = stateService.createState(stateDto);
        return ResponseEntity.ok(createdStateDto);
    }
    @Tag(name = "State")
    @Operation(summary = "Update a state by ID", description = "Update a single state by it's ID. Returns created state")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "State successfully updated"),
        @ApiResponse(responseCode = "404", description = "State not found"),
        @ApiResponse(responseCode =  "401", description = "Unauthorized")
    })
    @PutMapping("/state/{stateId}")
    public ResponseEntity<StateDto> updateState(@PathVariable Integer stateId, @RequestBody StateDto stateDto, @RequestHeader("api-key") String apiKey) { 
        apiKeyValidator.validate(apiKey);
        StateDto updatedStateDto =  stateService.updateState(stateId, stateDto);
        return ResponseEntity.ok(updatedStateDto);
    }
    @Tag(name = "State")
    @Operation(summary = "Delete a state by ID", description = "Delete a single state by it's ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "State successfully deleted"),
        @ApiResponse(responseCode = "404", description = "State not found"),
        @ApiResponse(responseCode =  "401", description = "Unauthorized")
    })
    @DeleteMapping("/state/{stateId}")
    public ResponseEntity<Void> deleteState(@PathVariable Integer stateId, @RequestHeader("api-key") String apiKey) {
        apiKeyValidator.validate(apiKey);
        stateService.deleteState(stateId);
        return ResponseEntity.noContent().build();
    }
}
