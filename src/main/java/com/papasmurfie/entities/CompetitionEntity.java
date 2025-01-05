package com.papasmurfie.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity(name = "Competitions")
public class CompetitionEntity extends PanacheEntity {
    @Column(length = 50, nullable = false)
    private String competitionName;
    @Column(nullable = false)
    private LocalDate competitionDate;

    public String getCompetitionName() {
        return competitionName;
    }

    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    public LocalDate getCompetitionDate() {
        return competitionDate;
    }

    public void setCompetitionDate(LocalDate competitionDate) {
        this.competitionDate = competitionDate;
    }
}
