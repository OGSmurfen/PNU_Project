package com.papasmurfie.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import java.time.LocalDate;

/**
 * Represents a competition entity stored in the database.
 * <p>
 * This class maps to the "Competitions" table in the database and holds the details of a competition, including
 * the competition's name and date. It extends {@link PanacheEntity} to leverage Quarkus' Panache ORM capabilities,
 * providing convenient methods for database operations.
 * </p>
 */
@Entity(name = "Competitions")
public class CompetitionEntity extends PanacheEntity {
    @Column(length = 50, nullable = false)
    private String competitionName;
    @Column(nullable = false)
    private LocalDate competitionDate;

    /**
     * Gets competition name.
     *
     * @return the competition name
     */
    public String getCompetitionName() {
        return competitionName;
    }

    /**
     * Sets competition name.
     *
     * @param competitionName the competition name
     */
    public void setCompetitionName(String competitionName) {
        this.competitionName = competitionName;
    }

    /**
     * Gets competition date.
     *
     * @return the competition date
     */
    public LocalDate getCompetitionDate() {
        return competitionDate;
    }

    /**
     * Sets competition date.
     *
     * @param competitionDate the competition date
     */
    public void setCompetitionDate(LocalDate competitionDate) {
        this.competitionDate = competitionDate;
    }
}
