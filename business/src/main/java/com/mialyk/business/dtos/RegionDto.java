package com.mialyk.business.dtos;

import java.io.Serializable;

import com.mialyk.persistence.entities.Region;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionDto implements Serializable  {
//    private Long id;
    private String name;

    public RegionDto(Region region) {

        if (region.getRegionName() != null) {
            this.name = region.getRegionName();
        }
    }

}

