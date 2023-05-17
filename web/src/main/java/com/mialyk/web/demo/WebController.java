package com.mialyk.web.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.mialyk.business.services.RegionService;
import com.mialyk.business.services.StateService;

@Controller
public class WebController {
    private RestTemplate restTemplate = new RestTemplate();
    @Autowired
    private StateService stateService;
    @Autowired
    private RegionService regionService;
    @Autowired
    private Environment env;

    @Value("${hat.google-maps-api-key}")
    private String googleMapsApiKey;

    @Value("${hat.default-state}")
    private String defaultState;

    @GetMapping("/")
    public String getStarter(Model model) {
        return "index";
    }

    @GetMapping("/charts")
    public String getPieChart(Model model) {

        String url = String.format("%s/hat/api/historical/values/state/%s", getBaseUrl(), defaultState);
        String historicalData = restTemplate.getForObject(url, String.class);

        model.addAttribute("regionTypes", regionService.getRegionTypes());
        model.addAttribute("states", stateService.getStateDtos());
        model.addAttribute("defaultStateHistoricalData", historicalData);
        model.addAttribute("defaultState", defaultState);
        return "charts";
    }

    @GetMapping("/maps")
    public String getActualData(Model model) {

        String url = String.format("%s/hat/api/analytics/states", getBaseUrl());
        String result = restTemplate.getForObject(url, String.class);

        model.addAttribute("defaultStateAnalytics", result);
        return "maps";
    }

    private String getBaseUrl() {
        String port = env.getProperty("server.port");
        String host = env.getProperty("server.host");
        Boolean sslEnabled = env.getProperty("server.ssl.enabled", Boolean.class, false);
        String protocol = sslEnabled ? "https" : "http";
        return String.format("%s://%s:%s", protocol, host, port);
    }
}
