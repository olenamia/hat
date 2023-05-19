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
@Table(name = "metro")
@DiscriminatorValue("METRO")
@NoArgsConstructor
@Getter
@Setter
public class MetroArea extends Region {

    @Column(name = "size_rank", columnDefinition = "INT")
    private Integer sizeRank;

    @OneToMany(mappedBy = "metroValue", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<HomeValue> homeValues;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "state_id")
    private State state;
}
