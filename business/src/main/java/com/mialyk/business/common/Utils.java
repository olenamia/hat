package com.mialyk.business.common;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {
    public static Double roundDouble(Double input){
        BigDecimal bd = new BigDecimal(input).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static Double roundDouble(BigDecimal input){
        BigDecimal bd = input.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
