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

import com.mialyk.business.dtos.HomeValueDto;
import com.mialyk.business.services.HomeValueService;
import com.mialyk.web.security.ApiKeyValidator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Home Value", description = "Endpoints to manage a home values")
@RestController
@RequestMapping("hat/api/homevalue")
public class HomeValueController {
    @Autowired
    private HomeValueService homeValueService;
    @Autowired
    private ApiKeyValidator apiKeyValidator;

    @GetMapping("/{homeValueId}")
    @Operation(summary = "Get a home value by ID", description = "Get a single home value by its ID and. Returns a home value: date and value")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Home value successfully returned"),
        @ApiResponse(responseCode = "404", description = "Home value not found")
    })
    public ResponseEntity<HomeValueDto> getHomeValue(@PathVariable Integer homeValueId) { 
        HomeValueDto homeValueDto =  homeValueService.getHomeValue(homeValueId);
        return ResponseEntity.ok(homeValueDto);
    }

    @PostMapping("")
    @Operation(summary = "Create a home value", description = "Create a single home value. Returns created home value")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Home value successfully created"),
        @ApiResponse(responseCode =  "401", description = "Unauthorized")
    })
    public ResponseEntity<HomeValueDto> createHomeValue(@RequestBody HomeValueDto homeValueDto, @RequestHeader("apiKey") String apiKey) { 
        apiKeyValidator.validate(apiKey);
        HomeValueDto newHomeValueDto = homeValueService.createHomeValue(homeValueDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newHomeValueDto);
    }

    @PutMapping("/{homeValueId}")
    @Operation(summary = "Update a home value by ID", description = "Update a single home value by it's ID. Returns updated home value")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Home value successfully updated"),
        @ApiResponse(responseCode = "404", description = "Home value not found"),
        @ApiResponse(responseCode =  "401", description = "Unauthorized")
    })
    public ResponseEntity<HomeValueDto> updateHomeValue(@PathVariable Integer homeValueId, @RequestBody HomeValueDto homeValueDto, @RequestHeader("apiKey") String apiKey) { 
        apiKeyValidator.validate(apiKey);
        HomeValueDto updatedHomeValueDto =  homeValueService.updateHomeValue(homeValueId, homeValueDto);
        return ResponseEntity.ok(updatedHomeValueDto);
    }

    @DeleteMapping("/{homeValueId}")
    @Operation(summary = "Delete a home value by ID", description = "Delete a single home value by it's ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Home value successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Home value not found"),
        @ApiResponse(responseCode =  "401", description = "Unauthorized")
    })
    public ResponseEntity<Void> deleteHomeValue(@PathVariable Integer homeValueId, @RequestHeader("apiKey") String apiKey) {
        apiKeyValidator.validate(apiKey);
        homeValueService.deleteHomeValue(homeValueId);
        return ResponseEntity.noContent().build();
    }
}
