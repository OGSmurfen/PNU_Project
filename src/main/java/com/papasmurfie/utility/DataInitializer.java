package com.papasmurfie.utility;

import com.papasmurfie.dto.CompetitionDTO;
import com.papasmurfie.entities.CompetitionEntity;
import com.papasmurfie.resources.CompetitionResource;
import com.papasmurfie.uow.IUnitOfWork;
import io.quarkus.runtime.Startup;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@Startup
@ApplicationScoped
public class DataInitializer {

    @Inject
    IUnitOfWork unitOfWork;

    @PostConstruct
    public void init() {
        List<CompetitionEntity> competitionDTOS = unitOfWork.getCompetitionsRepository().findAll().stream().toList();

        if(competitionDTOS.isEmpty()) {
           populateCompetitions();
        }

    }

    @Transactional
    public void populateCompetitions() {
        CompetitionEntity competition1 = new CompetitionEntity();
        competition1.setCompetitionName("Bulgarian Cup I 2022");
        competition1.setCompetitionDate(LocalDate.of(2022, 10, 10));

        CompetitionEntity competition2 = new CompetitionEntity();
        competition2.setCompetitionName("Bulgarian Cup I 2023");
        competition2.setCompetitionDate(LocalDate.of(2023,10,10));

        CompetitionEntity competition3 = new CompetitionEntity();
        competition3.setCompetitionName("Bulgarian Cup I 2024");
        competition3.setCompetitionDate(LocalDate.of(2024,10,10));

        CompetitionEntity competition4 = new CompetitionEntity();
        competition4.setCompetitionName("Bulgarian Cup III 2022");
        competition4.setCompetitionDate(LocalDate.of(2022,5,25));

        CompetitionEntity competition5 = new CompetitionEntity();
        competition5.setCompetitionName("Bulgarian Cup III 2023");
        competition5.setCompetitionDate(LocalDate.of(2023,5,25));

        CompetitionEntity competition6 = new CompetitionEntity();
        competition6.setCompetitionName("Bulgarian Cup IV 2025");
        competition6.setCompetitionDate(LocalDate.of(2025,01,07));

        unitOfWork.getCompetitionsRepository().persist(competition1);
        unitOfWork.getCompetitionsRepository().persist(competition2);
        unitOfWork.getCompetitionsRepository().persist(competition3);
        unitOfWork.getCompetitionsRepository().persist(competition4);
        unitOfWork.getCompetitionsRepository().persist(competition5);
        unitOfWork.getCompetitionsRepository().persist(competition6);
    }
}
