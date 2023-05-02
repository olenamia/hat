package com.mialyk.business.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mialyk.business.dtos.AnalyticsDto;
import com.mialyk.business.dtos.HomeValueDto;
import com.mialyk.business.dtos.StateDto;
import com.mialyk.persistence.entities.Region;
import com.mialyk.persistence.entities.State;
import com.mialyk.persistence.entities.HomeValue.RegionType;
import com.mialyk.persistence.entities.HomeValue;
import com.mialyk.persistence.repositories.AnalyticsRepository;
//import com.mialyk.persistence.entities.HomeValueZillow.RegionType;
import com.mialyk.persistence.repositories.HomeValueRepository;
import com.mialyk.persistence.views.StateAnalyticsView;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;


@Service
public class HomeValueService {
    @Autowired
    private HomeValueRepository homeValueZillowRepository;
    //@Autowired
    //private AnalyticsRepository analyticsRepository;
    @Autowired
    private RegionService regionService;

    public Date getMaxDateByStateName(String stateName) {
        return homeValueZillowRepository.findMaxDateByStateName(stateName);
    }
    @Transactional
    public List<HomeValueDto> getHomeValuesByState(String stateName) {//throws JsonProcessingException, JsonMappingException {

        Date maxDateByState = homeValueZillowRepository.findMaxDateByStateName(stateName);
        if (maxDateByState != null){
            List<Object[]> homeValuesList = homeValueZillowRepository.getYearlyHomeValuesByState(stateName);

            return homeValuesList.stream().map(homeValue -> {
                try {
                    return new HomeValueDto(
                        (PGobject)homeValue[0], 
                        homeValue[1] == null ? null : ((BigDecimal)homeValue[1]).doubleValue(),
                        stateName);
                } catch (ParseException e) {

                    e.printStackTrace();
                }
                return null;
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Transactional
    public List<AnalyticsDto> GetAnalyticsForStates() throws JsonMappingException, JsonProcessingException{
  
        List<AnalyticsDto> analyticsList =  new ArrayList<>();
        for (Object[] row : homeValueZillowRepository.GetAnalyticsForStates()) {

            Date date = (Date) row[0];
            Double homeValue = ((BigDecimal)row[1]).doubleValue();

            PGobject pgObject = ((PGobject)row[2]);
            String[] values = pgObject.getValue().substring(1, pgObject.getValue().length() - 1).split(",");
            StateDto regionDto = new StateDto(Integer.parseInt(values[2]), values[1].replace("\"", ""), values[4]);

            Date prevYearDate = (Date) row[3];
            Double prevYearValue = ((BigDecimal)row[4]).doubleValue();

            DecimalFormat format = new DecimalFormat("#.##");
            format.setRoundingMode(RoundingMode.HALF_UP);

            Double yoyChange = Double.valueOf(format.format(((BigDecimal)row[5])));
            Double momChange = Double.valueOf(format.format(((BigDecimal)row[6])));
            Date prevMonthDate = (Date) row[7];
            Double prevMonthValue =((BigDecimal)row[8]).doubleValue();

            AnalyticsDto analytics = new AnalyticsDto(date, homeValue, regionDto, prevYearDate, prevYearValue, yoyChange, momChange, prevMonthDate, prevMonthValue);
            analyticsList.add(analytics);
        }
        return analyticsList;
    }

    /* 
    @Transactional
    public List<StateAnalyticsView> GetAnalyticsForStates() {

        List<StateAnalyticsView> result = homeValueZillowRepository.GetAnalyticsForStates();

        return result;
    }*/

    private Date getMaxHomeValueDate (Region region, RegionType regionType) {


        Date lastAddedDate = homeValueZillowRepository.findMaxDateByRegionIdAndRegionType(region.getRegionId(), regionType.name());

        return lastAddedDate;

    }

}