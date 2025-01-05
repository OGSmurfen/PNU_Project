package com.papasmurfie.uow;


import com.papasmurfie.repositories.*;

public interface IUnitOfWork {

    CompetitionsRepository getCompetitionsRepository();
    CompetitorsRepository getCompetitorsRepository();
    EventsRepository getEventsRepository();
    NationalitiesRepository getNationalitiesRepository();
    ParticipationsRepository getParticipationsRepository();
    ResultsRepository getResultsRepository();



}
