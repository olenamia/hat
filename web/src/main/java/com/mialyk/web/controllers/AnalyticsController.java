package com.mialyk.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mialyk.business.dtos.AnalyticsDto;
import com.mialyk.business.services.AnalyticsService;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Tag(name = "Recent Trends Analytics", description = "Endpoints to get recent trends analytics for different region types")
@RestController
@RequestMapping("hat/api/analytics")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping({"/states"})
    @Operation(summary = "Recent trends by state", description = "Returns recent home value and year-over-year change for US states")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode =  "500", description = "Internal Server Error")
    })
    public ResponseEntity<List<AnalyticsDto>> getAnalyticsForStates() { 
        List<AnalyticsDto> statesAnalytics = analyticsService.GetAnalyticsForStates();
        return ResponseEntity.ok(statesAnalytics);
    }
}
