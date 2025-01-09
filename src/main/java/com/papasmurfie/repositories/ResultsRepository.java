package com.papasmurfie.repositories;

import com.papasmurfie.entities.ResultEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * The {@link ResultsRepository} class provides the repository layer for managing {@link ResultEntity} entities.
 * <p>
 * This class implements {@link PanacheRepository}, which offers a set of built-in methods for common database operations,
 * including CRUD operations such as find, persist, delete, and more, specifically for {@link ResultEntity} objects.
 * </p>
 * <p>
 * The class is annotated with {@link ApplicationScoped}, ensuring it is a singleton managed by the Quarkus framework
 * and is available for dependency injection across the application.
 * </p>
 */
@ApplicationScoped
public class ResultsRepository implements PanacheRepository<ResultEntity> {
}
