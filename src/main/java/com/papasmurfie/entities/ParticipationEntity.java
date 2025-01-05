package com.papasmurfie.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

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

    public CompetitorEntity getCompetitor() {
        return competitor;
    }

    public void setCompetitor(CompetitorEntity competitor) {
        this.competitor = competitor;
    }

    public CompetitionEntity getCompetition() {
        return competition;
    }

    public void setCompetition(CompetitionEntity competition) {
        this.competition = competition;
    }

    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public ResultEntity getResult() {
        return result;
    }

    public void setResult(ResultEntity result) {
        this.result = result;
    }
}
