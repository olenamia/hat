package com.mialyk.persistence.entities;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "metro")
@DiscriminatorValue("METRO")
@NoArgsConstructor
@Data
public class MetroArea extends Region {

    @Column(name = "size_rank", columnDefinition = "INT")
    private int sizeRank;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private State state;

    //@OneToMany(mappedBy = "metro")
    //private List<County> counties;
}