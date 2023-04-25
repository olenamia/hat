package com.mialyk.persistence.entities;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "home_value_zillow")
@Data
@AllArgsConstructor
@NoArgsConstructor
//@DiscriminatorColumn(name = "region_type")
public class HomeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    //@Column(name = "id", columnDefinition = "BIGINT")
    private int id;
    
    @Column(name = "value", columnDefinition = "DECIMAL(9, 2)")
    private BigDecimal value;

    @Temporal(TemporalType.DATE)
    @Column(name = "date", columnDefinition = "DATE")
    private Date date;

    //@ManyToOne
    @Enumerated(EnumType.STRING)
    //@JoinColumn(columnDefinition = "region_type")
    @Column(name = "region_type", insertable=true, updatable=false)
    private RegionType regionType;

    // TODO: remove (cascade = CascadeType.PERSIST)
    /* To resolve this issue, you need to save the State object before you save the HomeValueZillow object. 
        You can do this by calling the save() or saveAndFlush() method on the StateRepository 
        (assuming that you have defined a repository for the State entity) 
        before you save the HomeValueZillow object. 
    */
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinTable(name = "home_values_state",
            joinColumns = @JoinColumn(name = "home_id"),
            inverseJoinColumns = @JoinColumn(name = "state_id"),
            //uniqueConstraints = @UniqueConstraint(columnNames = {"home_id", "state_id"}))
            uniqueConstraints = @UniqueConstraint(columnNames = {"home_id"}))
    private State stateValue;

    @OneToOne
    @JoinTable(name = "home_values_county",
            joinColumns = @JoinColumn(name = "home_id"),
            inverseJoinColumns = @JoinColumn(name = "county_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"home_id"}))
    private County countyValue;

    @OneToOne
    @JoinTable(name = "home_values_metro",
            joinColumns = @JoinColumn(name = "home_id"),
            inverseJoinColumns = @JoinColumn(name = "metro_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"home_id"}))
    private MetroArea metroValue;

    @OneToOne
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
        else if (regionType == RegionType.COUNTY && region instanceof MetroArea) {
            metroValue = (MetroArea)region;
        }
        else if (regionType == RegionType.COUNTRY && region instanceof Country) {
            countryValue = (Country)region;
        }
    }
    
    public enum RegionType {
        STATE,
        COUNTY,
        METRO,
        // CITY,
        // ZIP,
        COUNTRY
    }


}









