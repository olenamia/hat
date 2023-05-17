package com.mialyk.persistence.views;

import java.sql.Date;

public interface StateAnalyticsView {

    Double getValue();
    Date getDate();
    Double getPrevMonthValue();
    Date getPrevMonthDate();
    Double getPrevYearValue();
    Date getPrevYearDate();

    Double getMomChange();
    Double getYoyChange();
    
    String getStateRegionName();
    Integer getStateRegionId();
    String getStateShortName();
}
