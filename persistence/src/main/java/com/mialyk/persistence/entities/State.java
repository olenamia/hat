package com.mialyk.persistence.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "state")
@DiscriminatorValue("STATE")
@NoArgsConstructor
@Data
public class State extends Region {

    @Column(name = "state_name")
    private String stateName;
    
    @Column(name = "size_rank", columnDefinition = "INT")
    private int sizeRank;

    @OneToMany(mappedBy = "state")
    private List<County> counties;

    @OneToMany(mappedBy = "state")
    private List<MetroArea> metros;
}


