package com.mialyk.persistence.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "county")
@DiscriminatorValue("COUNTY")
@NoArgsConstructor
@Getter
@Setter
public class County extends Region {

    @Column(name = "size_rank", columnDefinition = "INT")
    private Integer sizeRank;

    @Column(name = "state_code_fips", columnDefinition = "INT")
    private Integer stateCodeFips;

    @Column(name = "metro_code_fips", columnDefinition = "INT")
    private Integer metroCodeFips;

    @OneToMany(mappedBy = "countyValue", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<HomeValue> homeValues;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "state_id")
    private State state;

    @Column(name = "metro_state")
    private String metroState;
}
