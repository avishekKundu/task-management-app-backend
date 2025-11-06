package com.webapp.taskmanagement.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ROIUtils {

    /**
     * Safe ROI computation: if timeTaken is null or zero or revenue is null -> returns null (caller can decide representation)
     */
    public static BigDecimal computeROI(BigDecimal revenue, BigDecimal timeTaken) {
        if (revenue == null || timeTaken == null) return null;
        if (timeTaken.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
        }
        BigDecimal roi = revenue.divide(timeTaken, 8, RoundingMode.HALF_UP);
        return roi.setScale(2, RoundingMode.HALF_UP);
    }

}
