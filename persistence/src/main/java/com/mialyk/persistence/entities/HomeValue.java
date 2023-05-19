package com.mialyk.persistence.entities;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "home_value_zillow", indexes = {
    @Index(name = "region_type_date_home_value_zillow_idx", columnList = "region_type, date")
})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;
    
    @Column(name = "value", columnDefinition = "DECIMAL(9, 2)")
    private BigDecimal value;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", columnDefinition = "DATE")
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "region_type", insertable=true, updatable=false)
    private RegionType regionType;

    @ManyToOne
    @JoinTable(name = "home_values_state",
            joinColumns = @JoinColumn(name = "home_id"),
            inverseJoinColumns = @JoinColumn(name = "state_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"home_id"}))
    private State stateValue;

    @ManyToOne
    @JoinTable(name = "home_values_county",
            joinColumns = @JoinColumn(name = "home_id"),
            inverseJoinColumns = @JoinColumn(name = "county_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"home_id"}))
    private County countyValue;

    @ManyToOne
    @JoinTable(name = "home_values_metro",
            joinColumns = @JoinColumn(name = "home_id"),
            inverseJoinColumns = @JoinColumn(name = "metro_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"home_id"}))
    private MetroArea metroValue;

    @ManyToOne
    @JoinTable(name = "home_values_country",
            joinColumns = @JoinColumn(name = "home_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"home_id"}))
    private Country countryValue;

    public void setRegion(Region region) {
        if (regionType == RegionType.STATE && region instanceof State) {
            stateValue = (State)region;
        }
        else if (regionType == RegionType.COUNTY && region instanceof County) {
            countyValue = (County)region;
        }
        else if (regionType == RegionType.METRO && region instanceof MetroArea) {
            metroValue = (MetroArea)region;
        }
        else if (regionType == RegionType.COUNTRY && region instanceof Country) {
            countryValue = (Country)region;
        }
    }
}
