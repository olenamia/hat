package com.mialyk.persistence.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mialyk.persistence.entities.HomeValue;
import com.mialyk.persistence.entities.HomeValue.RegionType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


/*@SqlResultSetMapping(
    name = "HomeValueZillowDtoMapping",
    classes = {
        @ConstructorResult(
            targetClass = HomeValueZillowDto.class,
            columns = {
                @ColumnResult(name = "hvz", type = HomeValueZillow.class),
                @ColumnResult(name = "value_change_pct", type = Double.class)
            }
        )
    }
)*/
@Repository
public interface HomeValueRepository extends JpaRepository<HomeValue, Integer> {
    
    //@Query("SELECT DISTINCT hvz.RegionType FROM HomeValueZillow hvz")
    //List<RegionType> findDistinctRegionTypes();
    //RegionType findDistinctRegionTypes();
    /*
    """
        SELECT MAX(hv.date) FROM home_value_zillow hvz 
            JOIN CASE :regionType 
                WHEN 'STATE' 
                    THEN home_values_state hvs ON hv.id = hvs.home_id 
                        JOIN state s ON s.id = hvs.state_id 
                WHEN 'METRO' 
                    THEN home_values_metro hvm ON hv.id = hvm.home_id 
                        JOIN metro m ON m.id = hvm.metro_id 
                WHEN 'COUNTRY' 
                    THEN home_values_country hvc ON hv.id = hvc.home_id 
                        JOIN country c ON c.id = hvc.country_id
                WHEN 'COUNTY' 
                    THEN home_values_county hvct ON hv.id = hvc.home_id 
                        JOIN county ct ON ct.id = hvct.country_id  
            END 
            WHERE r.regionId = :regionId
        """*/
/*
    @Query(value = """
        SELECT MAX(hv.date) FROM home_value_zillow hv 
            JOIN CASE :regionType 
                WHEN 'STATE' 
                    THEN home_values_state hvs ON hv.id = hvs.home_id 
                        JOIN state r ON r.id = hvs.state_id 
                WHEN 'METRO' 
                    THEN home_values_metro hvm ON hv.id = hvm.home_id 
                        JOIN metro r ON r.id = hvm.metro_id 
                WHEN 'COUNTRY' 
                    THEN home_values_country hvc ON hv.id = hvc.home_id 
                        JOIN country r ON r.id = hvc.country_id
                WHEN 'COUNTY' 
                    THEN home_values_county hvct ON hv.id = hvc.home_id 
                        JOIN county r ON r.id = hvct.country_id  
            END 
            WHERE r.regionId = :regionId
            """,
        nativeQuery = true)*/
        /* 
        @Query(value = """
            SELECT MAX(hv.date) FROM home_value_zillow hv 
                JOIN CASE :regionType 
                    WHEN 'STATE' 
                        THEN home_values_state hvs ON hv.id = hvs.home_id 
                            JOIN state s ON s.id = hvs.state_id 
                    WHEN 'METRO' 
                        THEN home_values_metro hvm ON hv.id = hvm.home_id 
                            JOIN metro m ON m.id = hvm.metro_id 
                    WHEN 'COUNTRY' 
                        THEN home_values_country hvc ON hv.id = hvc.home_id 
                            JOIN country c ON c.id = hvc.country_id
                    WHEN 'COUNTY' 
                        THEN home_values_county hvct ON hv.id = hvct.home_id 
                            JOIN county ct ON ct.id = hvct.country_id  
                END 
                WHERE 
                    CASE :regionType
                        WHEN 'STATE' THEN s.region_id = :regionId
                        WHEN 'METRO' THEN m.region_id = :regionId
                        WHEN 'COUNTRY' THEN c.region_id = :regionId
                        WHEN 'COUNTY' THEN ct.region_id = :regionId
                    END 
            """,
        nativeQuery = true) */
        @Query(value = """
            SELECT MAX(hv.date)
FROM home_value_zillow hv
LEFT JOIN home_values_state hvs ON hv.id = hvs.home_id AND :regionType = 'STATE'
LEFT JOIN state s ON s.id = hvs.state_id AND :regionType = 'STATE'
LEFT JOIN home_values_metro hvm ON hv.id = hvm.home_id AND :regionType = 'METRO'
LEFT JOIN metro m ON m.id = hvm.metro_id AND :regionType = 'METRO'
LEFT JOIN home_values_country hvc ON hv.id = hvc.home_id AND :regionType = 'COUNTRY'
LEFT JOIN country c ON c.id = hvc.country_id AND :regionType = 'COUNTRY'
LEFT JOIN home_values_county hvct ON hv.id = hvct.home_id AND :regionType = 'COUNTY'
LEFT JOIN county ct ON ct.id = hvct.county_id AND :regionType = 'COUNTY'
WHERE
    COALESCE(s.region_id, m.region_id, c.region_id, ct.region_id) = :regionId
            """,
        nativeQuery = true)
    Date findMaxDateByRegionIdAndRegionType(@Param("regionId") Integer regionId, @Param("regionType") String regionType);


    @Query("SELECT MAX(hvz.date) FROM HomeValue hvz JOIN hvz.stateValue s WHERE s.regionName = :regionName")
    Date findMaxDateByStateName(@Param("regionName") String regionName);

    @Query(value = """
        WITH find_max_date AS (
            SELECT MAX(h1.date) AS max_date
                FROM home_value_zillow h1 
                JOIN home_values_state h2 ON h1.id = h2.home_id 
                JOIN state s2 ON s2.id = h2.state_id
                WHERE s2.region_name = :regionName
            )
        SELECT hvz, --.id, hvz.value, hvz.date, hvz.region_type, 
            100 * (hvz.value / lag(hvz.value) OVER (ORDER BY hvz.date) - 1) AS value_change_pct
            FROM home_value_zillow hvz 
            JOIN home_values_state hvs ON hvz.id = hvs.home_id 
            JOIN state s ON s.id = hvs.state_id
            CROSS JOIN find_max_date
            WHERE s.region_name = :regionName
                AND extract(month from hvz.date) = extract(month from (find_max_date.max_date))
            GROUP BY hvz.id, find_max_date.max_date
            ORDER BY hvz.date;
            """, 
        nativeQuery = true)//, resultSetMapping = "HomeValueZillowDtoMapping")
    List<Object[]> getYearlyHomeValuesByState(@Param("regionName") String regionName); 
    //List<HomeValueZillowDto> getYearlyHomeValuesByState(@Param("regionName") String regionName); 

    //hvz.id, hvz.value, hvz.date, hvz.region_type
    /* 
        WITH find_max_date AS (
            SELECT MAX(h1.date) AS max_date
                FROM home_value_zillow h1 
                JOIN home_values_state h2 ON h1.id = h2.home_id 
                JOIN state s2 ON s2.id = h2.state_id
                WHERE s2.region_name = :regionName
            )
        SELECT hvz.*, 
            100 * (hvz.value / lag(hvz.value) OVER (ORDER BY hvz.date) - 1) AS value_change_pct
            FROM home_value_zillow hvz 
            JOIN home_values_state hvs ON hvz.id = hvs.home_id 
            JOIN state s ON s.id = hvs.state_id
            CROSS JOIN find_max_date
            WHERE s.region_name = :regionName
                AND extract(month from hvz.date) = extract(month from (find_max_date.max_date))
            GROUP BY hvz.id, find_max_date.max_date
            ORDER BY hvz.date;
    */

}