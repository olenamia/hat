package com.mialyk.business.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mialyk.persistence.entities.State;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class StateDto extends RegionDto {

        private String shortName;

        public StateDto(State state) {
            super(state);
            if (state.getStateName() != null) {
                this.shortName = state.getStateName();
            }
        }
        public StateDto(Integer regionId, String name, String shortName) {
            super(regionId, name);
            this.shortName = shortName;
        }
}
