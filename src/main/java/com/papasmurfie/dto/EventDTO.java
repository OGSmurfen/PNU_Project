package com.papasmurfie.dto;

import java.math.BigDecimal;

public record EventDTO(
                        BigDecimal distance,
                        String eventType
                )
{
}
