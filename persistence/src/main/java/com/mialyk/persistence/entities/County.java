package com.mialyk.persistence.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "county")
@DiscriminatorValue("COUNTY")
@NoArgsConstructor
@Data
public class County extends Region {

    @Column(name = "size_rank", columnDefinition = "INT")
    private Integer sizeRank;

    @Column(name = "state_code_fips", columnDefinition = "INT")
    private Integer stateCodeFips;

    @Column(name = "metro_code_fips", columnDefinition = "INT")
    private Integer metroCodeFips;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private State state;

    //@ManyToOne(cascade = CascadeType.PERSIST)
    //private MetroArea metro;
    @Column(name = "metro_state")
    private String metroState;
}

