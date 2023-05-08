package com.mialyk.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mialyk.business.dtos.AnalyticsDto;
import com.mialyk.business.services.HomeValueService;

@RestController
@RequestMapping("hat/api/analytics")
public class AnalyticsController {
    @Autowired
    private HomeValueService homeValueZillowService;

    @GetMapping({"/states", "/states/"})
    public List<AnalyticsDto> getHomeValuesByState() { 
        return homeValueZillowService.GetAnalyticsForStates();
    }
}
