package com.mialyk.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mialyk.business.dtos.AnalyticsDto;
import com.mialyk.business.services.IHomeValueService;

@RestController
@RequestMapping("hat/api/analytics")
public class AnalyticsController {
    @Autowired
    private IHomeValueService homeValueZillowService;

    @GetMapping({"/states"})
    public List<AnalyticsDto> getHomeValuesForStates() { 
        return homeValueZillowService.GetAnalyticsForStates();
    }
}
