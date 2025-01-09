package com.papasmurfie.dto;

import java.math.BigDecimal;
/**
 * A Data Transfer Object (DTO) representing a competition event.
 * <p>
 * This record holds information about an event, including its distance and type.
 * It is immutable and serves as a lightweight data carrier between application layers.
 * </p>
 *
 * @param distance the distance of meters the event is
 * @param eventType the type of the event - a long run, sprint, dash etc.
 */
public record EventDTO(
                        BigDecimal distance,
                        String eventType
                )
{
}
