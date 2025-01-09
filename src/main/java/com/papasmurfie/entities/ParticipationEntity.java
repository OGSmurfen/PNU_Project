package com.papasmurfie.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;


/**
 * Represents a participation entity stored in the database.
 * <p>
 * This class maps to the "Participations" table in the database and holds the details of a competitor's participation
 * in a specific competition event, including the associated competitor, competition, event, and result.
 * It extends {@link PanacheEntity} to leverage Quarkus' Panache ORM capabilities, simplifying database interactions.
 * </p>
 */
@Entity(name = "Participations")
public class ParticipationEntity extends PanacheEntity {
    @ManyToOne
    private CompetitorEntity competitor;
    @ManyToOne
    private CompetitionEntity competition;
    @ManyToOne
    private EventEntity event;
    @OneToOne
    private ResultEntity result;

    /**
     * Gets competitor.
     *
     * @return the competitor
     */
    public CompetitorEntity getCompetitor() {
        return competitor;
    }

    /**
     * Sets competitor.
     *
     * @param competitor the competitor
     */
    public void setCompetitor(CompetitorEntity competitor) {
        this.competitor = competitor;
    }

    /**
     * Gets competition.
     *
     * @return the competition
     */
    public CompetitionEntity getCompetition() {
        return competition;
    }

    /**
     * Sets competition.
     *
     * @param competition the competition
     */
    public void setCompetition(CompetitionEntity competition) {
        this.competition = competition;
    }

    /**
     * Gets event.
     *
     * @return the event
     */
    public EventEntity getEvent() {
        return event;
    }

    /**
     * Sets event.
     *
     * @param event the event
     */
    public void setEvent(EventEntity event) {
        this.event = event;
    }

    /**
     * Gets result.
     *
     * @return the result
     */
    public ResultEntity getResult() {
        return result;
    }

    /**
     * Sets result.
     *
     * @param result the result
     */
    public void setResult(ResultEntity result) {
        this.result = result;
    }
}
