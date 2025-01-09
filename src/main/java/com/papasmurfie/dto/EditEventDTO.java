package com.papasmurfie.dto;

import java.math.BigDecimal;

/**
 * A Data Transfer Object (DTO) representing a request to edit an event's details.
 * <p>
 * This record holds both the current event details (distance and event type) and the updated details
 * (new distance and new event type) to modify the event's properties. It is immutable and serves as a
 * lightweight data carrier for passing edit requests between application layers.
 * </p>
 *
 * @param distance the current distance of the event
 * @param eventType the current type of the event
 * @param newDistance the new distance of the event (if being updated)
 * @param newEventType the new type of the event (if being updated)
 */
public record EditEventDTO (
        BigDecimal distance,
        String eventType,
        BigDecimal newDistance,
        String newEventType
){
}
