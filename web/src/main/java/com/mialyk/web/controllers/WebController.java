package com.mialyk.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.mialyk.business.services.CountyService;
import com.mialyk.business.services.MetroAreaService;
import com.mialyk.business.services.RegionService;
import com.mialyk.business.services.StateService;

@Controller
public class WebController {
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private StateService stateService;
    @Autowired
    private MetroAreaService metroAreaService;
    @Autowired
    private CountyService countyService;
    

    @Value("${hat.mapsApiKey}")
    private String googleMapsApiKey;

    @GetMapping("/")
    public String getStarter(Model model) {
        return "index";
    }

    @GetMapping("/charts/")
    public String getPieChart(Model model) {
        String url = "http://localhost:8080/hat/api/historical/values/state/California";
        String historicalData = restTemplate.getForObject(url, String.class);

        model.addAttribute("regionTypes", RegionService.getRegionTypes());

        model.addAttribute("states", stateService.getStateDtos());
        model.addAttribute("metros", metroAreaService.getMetroAreaDtos());
        model.addAttribute("counties", countyService.getCountyDtos());

        //StateDto selectedState = null;
        //model.addAttribute("defaultState", new StateDto());
        model.addAttribute("defaultStateHistoricalData", historicalData);
        model.addAttribute("defaultState", "California");

        return "charts";
    }

    @GetMapping("/maps/")
    public String getActualData(Model model) {
        String url = "http://localhost:8080/hat/api/analytics/states";
        String result = restTemplate.getForObject(url, String.class);

        model.addAttribute("defaultStateAnalytics", result);
        return "maps";
    }
}
