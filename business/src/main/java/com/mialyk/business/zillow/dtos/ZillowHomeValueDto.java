package com.mialyk.business.zillow.dtos;

import org.apache.commons.collections4.MultiValuedMap;

import com.opencsv.bean.CsvBindAndJoinByName;
import com.opencsv.bean.CsvBindByName;

import lombok.Data;

@Data
public class ZillowHomeValueDto {

    @CsvBindByName(column = "RegionID")
    private int regionId;

    @CsvBindByName(column = "SizeRank")
    private int sizeRank;

    @CsvBindByName(column = "RegionName")
    private String regionName;

    /* Region types: country, msa, state, county, city, zip */
    @CsvBindByName(column = "RegionType")
    private String regionType;

    @CsvBindByName(column = "StateName")
    private String stateName;

    // Equal to StateName
    // county, city, metro
    @CsvBindByName(column = "State")
    private String state;

    // county, city
    @CsvBindByName(column = "Metro")
    private String metro;

    // county
    @CsvBindByName(column = "StateCodeFIPS")
    private int stateCodeFIPS;
    
    // county
    @CsvBindByName(column = "MunicipalCodeFIPS")
    private int municipalCodeFIPS;

    // city
    @CsvBindByName(column = "CountyName")
    private String countyName;
    
    @CsvBindAndJoinByName(column = ".*", elementType = Double.class)
    private MultiValuedMap<String, Double> monthlyData;
}