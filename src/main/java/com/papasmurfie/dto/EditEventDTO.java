package com.papasmurfie.dto;

import java.math.BigDecimal;

public record EditEventDTO (
        BigDecimal distance,
        String eventType,
        BigDecimal newDistance,
        String newEventType
){
}
