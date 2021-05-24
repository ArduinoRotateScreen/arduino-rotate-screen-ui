package com.arnaugarcia.ars.ui.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class DeviceThreshold {

    private final Between verticalThreshold;
    private final Between horizontalThreshold;

    public static class Between {
        private Integer min;
        private Integer max;

        public static Between between(Integer min, Integer max) {
            final Between threshold = new Between();
            threshold.min = min;
            threshold.max = max;
            return threshold;
        }

        public Integer getMin() {
            return min;
        }

        public Integer getMax() {
            return max;
        }
    }
}
