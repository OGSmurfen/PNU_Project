package com.papasmurfie.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Represents an event entity stored in the database.
 * <p>
 * This class maps to the "Events" table in the database and holds details of an event, such as the event's distance
 * and type. It extends {@link PanacheEntity} to leverage Quarkus' Panache ORM capabilities, which simplify database
 * interactions.
 * </p>
 */
@Entity(name = "Events")
public class EventEntity extends PanacheEntity {
    @Column(nullable = false, unique = true)
    private BigDecimal distance; // Distance in some unit (e.g., meters)
    @Column(nullable = false)
    private String eventType;

    /**
     * Gets distance.
     * <p>
     * The distance is returned with a scale of two decimal places, rounded using
     * {@link RoundingMode#HALF_UP}.
     * </p>
     *
     * @return the distance
     *
     */
    public BigDecimal getDistance() {
        return distance;
    }

    /**
     * Sets distance.
     *
     * @param distance the distance
     */
    public void setDistance(BigDecimal distance) {
        this.distance = distance.setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Gets event type.
     *
     * @return the event type
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * Sets event type.
     *
     * @param eventType the event type
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
