package com.mialyk.business.mappers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mialyk.business.dtos.CountyDto;
import com.mialyk.persistence.entities.County;

@Component
public class CountyMapper {
    @Autowired
    private StateMapper stateDtoMapper;

    public County map(CountyDto countyDto) {
        County county = new County();

        county.setId(countyDto.getId());
        county.setRegionName(countyDto.getName());
        county.setRegionId(countyDto.getRegionId());
        county.setSizeRank(countyDto.getSizeRank());
        county.setStateCodeFips(countyDto.getStateCodeFips());
        county.setMetroCodeFips(countyDto.getMetroCodeFips());
        county.setState(stateDtoMapper.map(countyDto.getState()));
        county.setMetroState(countyDto.getMetroState());
        return county;
    }

    public CountyDto map(County county) {
        CountyDto countyDto = new CountyDto();

        countyDto.setId(county.getId());
        countyDto.setName(county.getRegionName());
        countyDto.setRegionId(county.getRegionId());
        countyDto.setSizeRank(county.getSizeRank());
        countyDto.setStateCodeFips(county.getStateCodeFips());
        countyDto.setMetroCodeFips(county.getMetroCodeFips());
        countyDto.setState(stateDtoMapper.map(county.getState()));
        countyDto.setMetroState(county.getMetroState());
        return countyDto;
    }
}
