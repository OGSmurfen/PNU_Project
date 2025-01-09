package com.papasmurfie.repositories;

import com.papasmurfie.entities.ParticipationEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * The {@link ParticipationsRepository} class provides the repository layer for managing {@link ParticipationEntity} entities.
 * <p>
 * This class implements {@link PanacheRepository}, which provides built-in methods for interacting with the database,
 * including standard CRUD operations such as find, persist, delete, and others for {@link ParticipationEntity} objects.
 * </p>
 * <p>
 * The class is annotated with {@link ApplicationScoped}, making it a singleton within the Quarkus framework and available
 * for dependency injection throughout the application's lifecycle.
 * </p>
 */
@ApplicationScoped
public class ParticipationsRepository implements PanacheRepository<ParticipationEntity> {
}
