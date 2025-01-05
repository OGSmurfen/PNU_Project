package com.papasmurfie.uow;

import com.papasmurfie.repositories.*;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UnitOfWork implements IUnitOfWork{

    private final CompetitionsRepository competitionsRepository;
    private final CompetitorsRepository competitorsRepository;
    private final EventsRepository eventsRepository;
    private final NationalitiesRepository nationalitiesRepository;
    private final ParticipationsRepository participationsRepository;
    private final ResultsRepository resultsRepository;

    public UnitOfWork(CompetitionsRepository competitionsRepository,
                      CompetitorsRepository competitorsRepository,
                      EventsRepository eventsRepository,
                      NationalitiesRepository nationalitiesRepository,
                      ParticipationsRepository participationsRepository,
                      ResultsRepository resultsRepository) {
        this.competitionsRepository = competitionsRepository;
        this.competitorsRepository = competitorsRepository;
        this.eventsRepository = eventsRepository;
        this.nationalitiesRepository = nationalitiesRepository;
        this.participationsRepository = participationsRepository;
        this.resultsRepository = resultsRepository;
    }

    @Override
    public CompetitionsRepository getCompetitionsRepository() {
        return competitionsRepository;
    }

    @Override
    public CompetitorsRepository getCompetitorsRepository() {
        return competitorsRepository;
    }

    @Override
    public EventsRepository getEventsRepository() {
        return eventsRepository;
    }

    @Override
    public NationalitiesRepository getNationalitiesRepository() {
        return nationalitiesRepository;
    }

    @Override
    public ParticipationsRepository getParticipationsRepository() {
        return participationsRepository;
    }

    @Override
    public ResultsRepository getResultsRepository() {
        return resultsRepository;
    }
}
