package com.papasmurfie.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity(name = "Events")
public class EventEntity extends PanacheEntity {
    @Column(nullable = false, unique = true)
    private BigDecimal distance; // Distance in some unit (e.g., meters)
    @Column(nullable = false)
    private String eventType;

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance.setScale(2, RoundingMode.HALF_UP);
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
