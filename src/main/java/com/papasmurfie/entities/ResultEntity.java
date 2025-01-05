package com.papasmurfie.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity(name = "Results")
public class ResultEntity extends PanacheEntity{
    @Column(nullable = false)
    private float seconds;
    @Column(nullable = false)
    private boolean finished;
    @Column(nullable = false)
    private String place;

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public float getSeconds() {
        return seconds;
    }

    public void setSeconds(float seconds) {
        this.seconds = Math.round(seconds * 1000.0f) / 1000.0f;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }
}
