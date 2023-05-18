package com.mialyk.persistence.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "state")
@DiscriminatorValue("STATE")
@NoArgsConstructor
@Getter
@Setter
public class State extends Region {

    @Column(name = "state_name")
    private String stateName;
    
    @Column(name = "size_rank", columnDefinition = "INT")
    private Integer sizeRank;

    @OneToMany(mappedBy = "stateValue", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<HomeValue> homeValues;

    @OneToMany(mappedBy = "state", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<County> counties;

    @OneToMany(mappedBy = "state", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private List<MetroArea> metros;
}


