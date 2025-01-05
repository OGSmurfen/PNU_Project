package com.papasmurfie.repositories;

import com.papasmurfie.entities.ResultEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResultsRepository implements PanacheRepository<ResultEntity> {
}
