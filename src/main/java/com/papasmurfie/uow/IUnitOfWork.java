package com.papasmurfie.uow;


import com.papasmurfie.repositories.*;

/**
 * The Unit of Work interface, responsible for encapsulating the interaction with the different repositories in the application.
 * This interface provides access to repositories for various entities such as Competitions, Competitors, Events,
 * Nationalities, Participations, and Results. The unit of work pattern is used to manage transactional consistency
 * across multiple repository operations.
 */
public interface IUnitOfWork {

    /**
     * Retrieves the repository for Competitions.
     *
     * @return The repository that handles operations related to competitions.
     */
    CompetitionsRepository getCompetitionsRepository();

    /**
     * Retrieves the repository for Competitors.
     *
     * @return The repository that handles operations related to competitors.
     */
    CompetitorsRepository getCompetitorsRepository();

    /**
     * Retrieves the repository for Events.
     *
     * @return The repository that handles operations related to events.
     */
    EventsRepository getEventsRepository();

    /**
     * Retrieves the repository for Nationalities.
     *
     * @return The repository that handles operations related to nationalities.
     */
    NationalitiesRepository getNationalitiesRepository();

    /**
     * Retrieves the repository for Participations.
     *
     * @return The repository that handles operations related to participations.
     */
    ParticipationsRepository getParticipationsRepository();

    /**
     * Retrieves the repository for Results.
     *
     * @return The repository that handles operations related to results.
     */
    ResultsRepository getResultsRepository();



}
