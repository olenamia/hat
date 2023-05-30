package com.mialyk.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.mialyk.business.services.CountyService;
import com.mialyk.web.security.ApiKeyValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "County", description = "Endpoints to manage a counties")
@RestController
@RequestMapping("hat/api/county")
public class CountyController {
    
    @Autowired
    private CountyService countyService;
    @Autowired
    private ApiKeyValidator apiKeyValidator;

    @GetMapping("/{countyId}")
    @Operation(summary = "Get a county by ID", description = "Get a single county by its ID and. Returns a county")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "County successfully rerurned"),
        @ApiResponse(responseCode = "404", description = "County not found"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public ResponseEntity<CountyDto> getCounty(@PathVariable Integer countyId) { 
        CountyDto countyDto =  countyService.getCounty(countyId);
        return ResponseEntity.ok(countyDto);
    }

    @PostMapping("")
    @Operation(summary = "Create a county", description = "Create a single county. Returns created county")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "County successfully created"),
        @ApiResponse(responseCode =  "401", description = "Unauthorized"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public ResponseEntity<CountyDto> createCounty(@RequestBody CountyDto countyDto, @RequestHeader("apiKey") String apiKey) { 
        apiKeyValidator.validate(apiKey);
        CountyDto newCountyDto = countyService.createCounty(countyDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newCountyDto);
    }

    @PutMapping("/{countyId}")
    @Operation(summary = "Update a county by ID", description = "Update a single county by it's ID. Returns updated county")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "County successfully updated"),
        @ApiResponse(responseCode = "404", description = "County not found"),
        @ApiResponse(responseCode =  "401", description = "Unauthorized"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public ResponseEntity<CountyDto> updateCounty(@PathVariable Integer countyId, @RequestBody CountyDto countyDto, @RequestHeader("apiKey") String apiKey) { 
        apiKeyValidator.validate(apiKey);
        CountyDto updatedCountyDto =  countyService.updateCounty(countyId, countyDto);
        return ResponseEntity.ok(updatedCountyDto);
    }

    @DeleteMapping("/{countyId}")
    @Operation(summary = "Delete a county by ID", description = "Delete a single county by it's ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "County successfully deleted"),
        @ApiResponse(responseCode = "404", description = "County not found"),
        @ApiResponse(responseCode =  "401", description = "Unauthorized"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> deleteCounty(@PathVariable Integer countyId, @RequestHeader("apiKey") String apiKey) {
        apiKeyValidator.validate(apiKey);
        countyService.deleteCounty(countyId);
        return ResponseEntity.noContent().build();
    }
}
