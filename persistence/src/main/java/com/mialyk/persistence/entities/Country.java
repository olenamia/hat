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
@Table(name = "country")
@DiscriminatorValue("COUNTRY")
@NoArgsConstructor
@Getter
@Setter
public class Country extends Region {

    @Column(name = "size_rank", columnDefinition = "INT")
    private Integer sizeRank;

    @OneToMany(mappedBy = "countryValue", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private List<HomeValue> homeValues;
}
