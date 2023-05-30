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

import com.mialyk.business.dtos.StateDto;
import com.mialyk.business.services.StateService;
import com.mialyk.web.security.ApiKeyValidator;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;

@Tag(name = "State", description = "Endpoints to manage a state")
@RestController
@RequestMapping("hat/api/state")
public class StateController {

    @Autowired
    private StateService stateService;
    @Autowired
    private ApiKeyValidator apiKeyValidator;

    @GetMapping("/{stateId}")
    @Operation(summary = "Get a state by ID", description = "Get a single state by it's ID. Returns a state")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "State successfully rerurned"),
        @ApiResponse(responseCode = "404", description = "State not found"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public ResponseEntity<StateDto> getState(@PathVariable Integer stateId) { 
        StateDto stateDto =  stateService.getState(stateId);
        return ResponseEntity.ok(stateDto);
    }

    @PostMapping("")
    @Operation(summary = "Create a state", description = "Create a single state. Returns created state")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "State successfully created"),
        @ApiResponse(responseCode =  "401", description = "Unauthorized"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public ResponseEntity<StateDto> createState(@RequestBody StateDto stateDto, @RequestHeader("apiKey") String apiKey) { 
        apiKeyValidator.validate(apiKey);
        StateDto createdStateDto = stateService.createState(stateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStateDto);
    }

    @PutMapping("/{stateId}")
    @Operation(summary = "Update a state by ID", description = "Update a single state by it's ID. Returns created state")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "State successfully updated"),
        @ApiResponse(responseCode = "404", description = "State not found"),
        @ApiResponse(responseCode =  "401", description = "Unauthorized"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public ResponseEntity<StateDto> updateState(@PathVariable Integer stateId, @RequestBody StateDto stateDto, @RequestHeader("apiKey") String apiKey) { 
        apiKeyValidator.validate(apiKey);
        StateDto updatedStateDto =  stateService.updateState(stateId, stateDto);
        return ResponseEntity.ok(updatedStateDto);
    }

    @DeleteMapping("/{stateId}")
    @Operation(summary = "Delete a state by ID", description = "Delete a single state by it's ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "State successfully deleted"),
        @ApiResponse(responseCode = "404", description = "State not found"),
        @ApiResponse(responseCode =  "401", description = "Unauthorized"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public ResponseEntity<Void> deleteState(@PathVariable Integer stateId, @RequestHeader("apiKey") String apiKey) {
        apiKeyValidator.validate(apiKey);
        stateService.deleteState(stateId);
        return ResponseEntity.noContent().build();
    }
}
