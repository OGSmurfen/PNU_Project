package com.papasmurfie.repositories;

import com.papasmurfie.entities.EventEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class EventsRepository implements PanacheRepository<EventEntity> {
}
