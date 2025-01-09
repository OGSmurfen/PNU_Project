package com.papasmurfie.repositories;

import com.papasmurfie.entities.CompetitionEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * The {@link CompetitionsRepository} class provides the repository layer for managing {@link CompetitionEntity} entities.
 * <p>
 * This class implements {@link PanacheRepository}, which provides various methods for interacting with the database
 * using Quarkus' Panache ORM. It is annotated with {@link ApplicationScoped}, making it available as a singleton
 * within the application's context for dependency injection.
 * </p>
 */
@ApplicationScoped
public class CompetitionsRepository implements PanacheRepository<CompetitionEntity> {
}
