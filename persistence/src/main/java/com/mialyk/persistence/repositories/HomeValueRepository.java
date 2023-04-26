package com.mialyk.persistence.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mialyk.persistence.entities.HomeValue;

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

}