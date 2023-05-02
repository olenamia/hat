package com.mialyk.persistence.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.mialyk.persistence.entities.HomeValue;
import com.mialyk.persistence.entities.State;
import com.mialyk.persistence.views.StateAnalyticsView;

import jakarta.persistence.NamedNativeQuery;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.SqlResultSetMapping;
import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.Entity;

import org.postgresql.util.PGobject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@NamedNativeQuery(
    name = "StateAnalyticsViewMapping",
    query = """
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
        resultSetMapping = "StateAnalyticsViewMapping"
)
@SqlResultSetMapping(
    name = "StateAnalyticsViewMapping",
    classes = @ConstructorResult(
        targetClass = StateAnalyticsView.class,
        columns = {
            @ColumnResult(name = "date", type = Date.class),
            @ColumnResult(name = "value", type = Double.class),
            @ColumnResult(name = "state", type = State.class),
            @ColumnResult(name = "prev_year_date", type = Date.class),
            @ColumnResult(name = "prev_year_value", type = Double.class),
            @ColumnResult(name = "yoy_change", type = Double.class),
            @ColumnResult(name = "prev_month_date", type = Date.class),
            @ColumnResult(name = "prev_month_value", type = Double.class),
            @ColumnResult(name = "mom_change", type = Double.class)
        }
    )
)
@Repository
public interface AnalyticsRepository{

   /*  @Query(value = """
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
        nativeQuery = true)*/
    @Query(nativeQuery = true, name = "StateAnalyticsViewMapping")
    List<StateAnalyticsView> GetAnalyticsForStates();
    
}
