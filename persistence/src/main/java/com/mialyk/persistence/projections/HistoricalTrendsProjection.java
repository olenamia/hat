package com.mialyk.persistence.projections;

import java.sql.Date;

public interface HistoricalTrendsProjection {

    Integer getId();
    Double getValue();
    Date getDate();
    Double getYoyChange();
}
