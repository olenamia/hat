package com.mialyk.persistence.repositories;

import java.sql.Date;
import java.util.List;

import com.mialyk.persistence.entities.HomeValue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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

    @Query(value = """
        WITH find_max_date AS (
            SELECT MAX(hv.date) AS max_date
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
            )
        SELECT hvz, --.id, hvz.value, hvz.date, hvz.region_type, 
            100 * (hvz.value / lag(hvz.value) OVER (ORDER BY hvz.date) - 1) AS value_change_pct
            FROM home_value_zillow hvz 
                LEFT JOIN home_values_state hvs ON hvz.id = hvs.home_id AND :regionType = 'STATE'
                LEFT JOIN state s ON s.id = hvs.state_id AND :regionType = 'STATE'
                LEFT JOIN home_values_metro hvm ON hvz.id = hvm.home_id AND :regionType = 'METRO'
                LEFT JOIN metro m ON m.id = hvm.metro_id AND :regionType = 'METRO'
                LEFT JOIN home_values_country hvc ON hvz.id = hvc.home_id AND :regionType = 'COUNTRY'
                LEFT JOIN country c ON c.id = hvc.country_id AND :regionType = 'COUNTRY'
                LEFT JOIN home_values_county hvct ON hvz.id = hvct.home_id AND :regionType = 'COUNTY'
                LEFT JOIN county ct ON ct.id = hvct.county_id AND :regionType = 'COUNTY'
            CROSS JOIN find_max_date
            WHERE 
                COALESCE(s.region_id, m.region_id, c.region_id, ct.region_id) = :regionId
                AND extract(month from hvz.date) = extract(month from (find_max_date.max_date))
            GROUP BY hvz.id, find_max_date.max_date
            ORDER BY hvz.date;
        """,
    nativeQuery = true)
    List<Object[]> getYearlyHomeValuesByRegionIdAndRegionType(@Param("regionId") Integer regionId, @Param("regionType") String regionType);


    @Query(value = """
        WITH find_max_date AS (
            SELECT MAX(hv.date) AS max_date
                FROM home_value_zillow hv
                WHERE region_type = 'STATE'
            ),
        prev_year AS (
            SELECT hv.date AS prev_year_date,
                    hv.value AS prev_year_value,
                    s.region_name AS prev_year_state
                FROM home_value_zillow hv
                    JOIN home_values_state hvs ON hv.id = hvs.home_id 
                    JOIN state s ON s.id = hvs.state_id
                    CROSS JOIN find_max_date
                WHERE region_type = 'STATE'
                    AND EXTRACT(MONTH FROM hv.date) = EXTRACT(MONTH FROM find_max_date.max_date)
                    AND EXTRACT(YEAR FROM hv.date) = EXTRACT(YEAR FROM find_max_date.max_date) - 1
            ),
        prev_month AS (
            SELECT hv.date AS prev_month_date,
                    hv.value AS prev_month_value,
                    s.region_name AS prev_month_state
                FROM home_value_zillow hv
                    JOIN home_values_state hvs ON hv.id = hvs.home_id 
                    JOIN state s ON s.id = hvs.state_id
                    CROSS JOIN find_max_date
                WHERE region_type = 'STATE'
                    AND EXTRACT(MONTH FROM hv.date) = EXTRACT(MONTH FROM find_max_date.max_date) - 1
                    AND EXTRACT(YEAR FROM hv.date) = EXTRACT(YEAR FROM find_max_date.max_date)
            )

        SELECT hvz.date , 
                hvz.value, 
                state, 
                prev_year.prev_year_date, 
                prev_year.prev_year_value,
                100 * (hvz.value / prev_year.prev_year_value - 1) AS yoy_change, 
                100 * (hvz.value / prev_month.prev_month_value - 1) AS mom_change, 
                prev_month.prev_month_date, 
                prev_month.prev_month_value 
        
            FROM home_value_zillow hvz 
                JOIN home_values_state hvs ON hvz.id = hvs.home_id 
                JOIN state ON state.id = hvs.state_id
            CROSS JOIN find_max_date
            CROSS JOIN prev_year
            CROSS JOIN prev_month
            WHERE 
                    hvz.region_type = 'STATE'
                    and hvz.date = find_max_date.max_date
                    and state.region_name = prev_year_state
                 and state.region_name = prev_month_state
        """,
        nativeQuery = true)
    List<Object[]> GetAnalyticsForStates();
}