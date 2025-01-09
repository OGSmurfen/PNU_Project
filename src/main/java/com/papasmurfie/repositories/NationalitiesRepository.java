package com.papasmurfie.repositories;

import com.papasmurfie.entities.NationalityEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * The {@link NationalitiesRepository} class provides the repository layer for managing {@link NationalityEntity} entities.
 * <p>
 * This class implements {@link PanacheRepository}, which offers built-in methods for interacting with the database,
 * including common CRUD operations such as find, persist, delete, and others for {@link NationalityEntity} objects.
 * </p>
 * <p>
 * The class is annotated with {@link ApplicationScoped}, ensuring it is managed as a singleton by the Quarkus framework,
 * available for dependency injection throughout the application's lifecycle.
 * </p>
 */
@ApplicationScoped
public class NationalitiesRepository implements PanacheRepository<NationalityEntity> {
}
