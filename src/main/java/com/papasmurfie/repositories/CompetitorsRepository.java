package com.papasmurfie.repositories;

import com.papasmurfie.entities.CompetitorEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CompetitorsRepository implements PanacheRepository<CompetitorEntity> {
}
