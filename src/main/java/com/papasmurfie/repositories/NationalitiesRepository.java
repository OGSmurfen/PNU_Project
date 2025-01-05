package com.papasmurfie.repositories;

import com.papasmurfie.entities.NationalityEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class NationalitiesRepository implements PanacheRepository<NationalityEntity> {
}
