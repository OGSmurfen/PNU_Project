package com.papasmurfie.uow;

import com.papasmurfie.repositories.*;
import jakarta.enterprise.context.ApplicationScoped;


/**
 * Implementation of the {@link IUnitOfWork} interface that provides access to various repositories.
 * The {@code UnitOfWork} class acts as a single point for accessing and managing repositories for different entities.
 * It is responsible for encapsulating the database interaction and ensuring transactional consistency across different
 * repository operations.
 *
 * <p>This class is annotated with {@link ApplicationScoped}, which makes it a CDI-managed bean and ensures it is
 * instantiated once per application lifecycle.</p>
 */
@ApplicationScoped
public class UnitOfWork implements IUnitOfWork{

    private final CompetitionsRepository competitionsRepository;
    private final CompetitorsRepository competitorsRepository;
    private final EventsRepository eventsRepository;
    private final NationalitiesRepository nationalitiesRepository;
    private final ParticipationsRepository participationsRepository;
    private final ResultsRepository resultsRepository;

    /**
     * Constructs a new {@code UnitOfWork} instance with the specified repositories injected by the DI container:
     *
     * @param competitionsRepository The repository responsible for handling competitions data.
     * @param competitorsRepository The repository responsible for handling competitors data.
     * @param eventsRepository The repository responsible for handling events data.
     * @param nationalitiesRepository The repository responsible for handling nationalities data.
     * @param participationsRepository The repository responsible for handling participations data.
     * @param resultsRepository The repository responsible for handling results data.
     */
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

    /**
     * Returns the repository for managing competitions.
     *
     * @return The {@link CompetitionsRepository} instance.
     */
    @Override
    public CompetitionsRepository getCompetitionsRepository() {
        return competitionsRepository;
    }

    /**
     * Returns the repository for managing competitors.
     *
     * @return The {@link CompetitorsRepository} instance.
     */
    @Override
    public CompetitorsRepository getCompetitorsRepository() {
        return competitorsRepository;
    }

    /**
     * Returns the repository for managing events.
     *
     * @return The {@link EventsRepository} instance.
     */
    @Override
    public EventsRepository getEventsRepository() {
        return eventsRepository;
    }

    /**
     * Returns the repository for managing nationalities.
     *
     * @return The {@link NationalitiesRepository} instance.
     */
    @Override
    public NationalitiesRepository getNationalitiesRepository() {
        return nationalitiesRepository;
    }

    /**
     * Returns the repository for managing participations.
     *
     * @return The {@link ParticipationsRepository} instance.
     */
    @Override
    public ParticipationsRepository getParticipationsRepository() {
        return participationsRepository;
    }

    /**
     * Returns the repository for managing results.
     *
     * @return The {@link ResultsRepository} instance.
     */
    @Override
    public ResultsRepository getResultsRepository() {
        return resultsRepository;
    }
}
