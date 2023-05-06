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
    private Integer id;
    private String name;

    public RegionDto(Region region) {
        this.id = region.getRegionId();

        if (region.getRegionName() != null) {
            this.name = region.getRegionName();
        }
    }
    public RegionDto(String regionName) {
            this.name = regionName;
    }
}

