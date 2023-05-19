package com.mialyk.business.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class StateDto extends RegionDto {

        private String shortName;
        private Integer sizeRank;

        public StateDto(Integer regionId, String name, String shortName) {
            super(regionId, name);
            this.shortName = shortName;
        }
}
