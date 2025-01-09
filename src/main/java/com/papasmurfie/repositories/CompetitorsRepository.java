package com.papasmurfie.repositories;

import com.papasmurfie.entities.CompetitorEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * The {@link CompetitorsRepository} class provides the repository layer for managing {@link CompetitorEntity} entities.
 * <p>
 * This class implements {@link PanacheRepository}, which provides a set of built-in methods for interacting with the database,
 * including basic CRUD operations such as persist, delete, and find.
 * </p>
 * <p>
 * The class is annotated with {@link ApplicationScoped}, which makes it a singleton that is managed by the Quarkus
 * framework and available for dependency injection throughout the application's lifecycle.
 * </p>
 */
@ApplicationScoped
public class CompetitorsRepository implements PanacheRepository<CompetitorEntity> {
}
