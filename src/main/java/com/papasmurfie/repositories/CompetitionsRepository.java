package com.papasmurfie.repositories;

import com.papasmurfie.entities.CompetitionEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompetitionsRepository implements PanacheRepository<CompetitionEntity> {
}
