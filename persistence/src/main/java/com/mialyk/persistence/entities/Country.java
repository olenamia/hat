package com.mialyk.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "country")
@DiscriminatorValue("COUNTRY")
@NoArgsConstructor
@Data
public class Country extends Region {

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "size_rank", columnDefinition = "INT")
    private int sizeRank;
}
