package com.papasmurfie.repositories;

import com.papasmurfie.entities.ParticipationEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;


@ApplicationScoped
public class ParticipationsRepository implements PanacheRepository<ParticipationEntity> {
}
