package com.papasmurfie.repositories;

import com.papasmurfie.entities.EventEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


/**
 * The {@link EventsRepository} class provides the repository layer for managing {@link EventEntity} entities.
 * <p>
 * This class implements {@link PanacheRepository}, which provides built-in methods for performing common database operations
 * such as finding, persisting, and deleting {@link EventEntity} objects.
 * </p>
 * <p>
 * The class is annotated with {@link ApplicationScoped}, meaning it will be managed as a singleton by the Quarkus framework,
 * and can be injected into other parts of the application as needed.
 * </p>
 */
@ApplicationScoped
public class EventsRepository implements PanacheRepository<EventEntity> {
}
