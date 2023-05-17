package com.mialyk.persistence.views;

import java.sql.Date;

public interface HistoricalTrendsView {

    Integer getId();
    Double getValue();
    Date getDate();
    Double getYoyChange();
}
