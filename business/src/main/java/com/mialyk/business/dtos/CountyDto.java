package com.mialyk.business.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class CountyDto extends RegionDto {

    private Integer sizeRank;
    private Integer stateCodeFips;
    private Integer metroCodeFips;
    private String metroState;
    private StateDto state;
}
