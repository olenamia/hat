package com.mialyk.business.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mialyk.persistence.entities.Region;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class RegionDto implements Serializable {
    private Integer id;
    private Integer regionId;
    private String name;

    public RegionDto(Region region) {
        this.id = region.getId();
        this.regionId = region.getRegionId();
        this.name = region.getRegionName();
    }

    public RegionDto(String regionName) {
        this.name = regionName;
    }

    public RegionDto(Integer regionId, String regionName) {
            this.regionId = regionId;
            this.name = regionName;
    }
}

