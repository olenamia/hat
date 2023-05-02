package com.mialyk.web.controllers;

import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.mialyk.business.dtos.StateDto;
import com.mialyk.business.services.MetroAreaService;
import com.mialyk.business.services.RegionService;
import com.mialyk.business.services.StateService;




@Controller
public class ChartsController {
    RestTemplate restTemplate = new RestTemplate();
    @Autowired
    StateService stateService;
    @Autowired
    RegionService regionService;
    @Autowired
    MetroAreaService metroAreaService;
    

    @Value("${hat.mapsApiKey}")
    private String googleMapsApiKey;

    @GetMapping("/chart/")
    public String getPieChart(Model model) {
        String url = "http://localhost:8080/hat/api/historical/values/state/California";
        String historicalData = restTemplate.getForObject(url, String.class);

        model.addAttribute("states", stateService.getStates());

        model.addAttribute("regionTypes", regionService.getRegionTypes());

        model.addAttribute("states", stateService.getStates());
        model.addAttribute("metros", metroAreaService.getMetroAreas());

        //StateDto selectedState = null;
        //model.addAttribute("defaultState", new StateDto());
        model.addAttribute("defaultStateHistoricalData", historicalData);
        model.addAttribute("defaultState", "California");

        return "chart";
    }

    @GetMapping("/")
    public String getStarter(Model model) {
        return "index";
    }

    
    @GetMapping("/map/")
    public String getActualData(Model model) {
        String url = "http://localhost:8080/hat/api/analitics/states";
        String result = restTemplate.getForObject(url, String.class);

        model.addAttribute("defaultStateAnalytics", result);
        return "map";
    }
}
