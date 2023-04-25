package com.mialyk.web.runner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class AppRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {}
/*
    @Autowired
    private LoadDataService loadDataService;
    @Autowired
    private HomeValueZillowService homeValueZillowService;
    @Autowired
    private HomeValueZillowRepository homeValueZillowRepository;

    @Override
    public void run(String... args) throws Exception {

        //homeValueZillowRepository.getYearlyHomeValuesByState("California");
        //homeValueZillowRepository.getYearlyHomeValuesByState("Florida");
        //List<HomeValueZillowDto> hvzs = homeValueZillowService.getHomeValuesByState("California");

        System.out.println("CA");

        // Add two hardcoded home_values for california
        //loadDataService.addHomeValue();

        // Test download csv to test.csv from Zillow site
        //(new FileDownloader()).download();

        // Add Home values from predefined short csv for 3 states
        
        /*
        ZHVIAllHomesStatesCsvParser allHomeStatesCsvParser = new ZHVIAllHomesStatesCsvParser();
        List<ZHVIStatesDto> zHVIStatesDtos = allHomeStatesCsvParser.getHomeValues();
        homeValueZillowService.saveHomeValues(zHVIStatesDtos);
        */
        
        
  /*   } */
}
