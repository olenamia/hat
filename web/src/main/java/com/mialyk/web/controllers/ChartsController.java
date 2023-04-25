package com.mialyk.web.controllers;

import java.util.Map;
import java.util.TreeMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;




@Controller
public class ChartsController {

    RestTemplate restTemplate = new RestTemplate();
    String url = "http://localhost:8080/hat/api/values/California";

    @GetMapping("/chart/")
    public String getPieChart(Model model) {
        String result = restTemplate.getForObject(url, String.class);


        Map<String, Integer> graphData = new TreeMap<>();
        graphData.put("2016", 147);
        graphData.put("2017", 1256);
        graphData.put("2018", 3856);
        graphData.put("2019", 19807);
        model.addAttribute("result", result);
        return "chart";
    }
}
