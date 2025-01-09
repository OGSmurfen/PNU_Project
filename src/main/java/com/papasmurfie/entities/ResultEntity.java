package com.papasmurfie.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;


/**
 * Represents a result entity stored in the database.
 * <p>
 * This class maps to the "Results" table in the database and holds the result information for a specific competitor's
 * performance in a competition event, including the time taken in seconds, whether the competitor finished, and their
 * place in the event.
 * It extends {@link PanacheEntity} to leverage Quarkus' Panache ORM capabilities, which simplifies database interactions.
 * </p>
 */
@Entity(name = "Results")
public class ResultEntity extends PanacheEntity{
    @Column(nullable = false)
    private float seconds;
    @Column(nullable = false)
    private boolean finished;
    @Column(nullable = false)
    private String place;

    /**
     * Gets place.
     *
     * @return the place
     */
    public String getPlace() {
        return place;
    }

    /**
     * Sets place.
     *
     * @param place the place
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * Gets seconds.
     *
     * @return the seconds
     */
    public float getSeconds() {
        return seconds;
    }

    /**
     * Sets seconds.
     *
     * @param seconds the seconds
     */
    public void setSeconds(float seconds) {
        this.seconds = Math.round(seconds * 1000.0f) / 1000.0f;
    }

    /**
     * Is finished boolean.
     *
     * @return the boolean
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Sets finished.
     *
     * @param finished the finished
     */
    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
