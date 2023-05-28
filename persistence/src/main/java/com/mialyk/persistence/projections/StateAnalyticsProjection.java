package com.mialyk.persistence.projections;

import java.sql.Date;

public interface StateAnalyticsProjection {

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
