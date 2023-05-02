package com.mialyk.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.mialyk.business.dtos.AnalyticsDto;
import com.mialyk.business.dtos.HomeValueDto;
import com.mialyk.business.services.HomeValueService;
import com.mialyk.persistence.views.StateAnalyticsView;

@RestController
@RequestMapping("hat")
public class HatController {
    @Autowired
    private HomeValueService homeValueZillowService;

    @GetMapping("/api/historical/values/state/{stateName}")
    public List<HomeValueDto> getHomeValuesByState(@PathVariable String stateName) throws JsonProcessingException, JsonMappingException {
        return homeValueZillowService.getHomeValuesByState(stateName);
    }

    @GetMapping("/api/analitics/states")
    public List<AnalyticsDto> getHomeValuesByState() throws JsonProcessingException, JsonMappingException {
        return homeValueZillowService.GetAnalyticsForStates();

    }

}
