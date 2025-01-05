package com.papasmurfie.services;

import com.papasmurfie.dto.CompetitionDTO;
import com.papasmurfie.entities.CompetitionEntity;
import com.papasmurfie.uow.IUnitOfWork;
import com.papasmurfie.utility.EntityValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class CompetitionsService {

    private final IUnitOfWork unitOfWork;

    public CompetitionsService(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    @Transactional
    public CompetitionDTO save(CompetitionDTO competitionDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("competitionName", competitionDTO.competitionName());
        map.put("competitionDate", competitionDTO.competitionDate());

        EntityValidator.validateUnique(
                unitOfWork.getCompetitionsRepository(),
                map,
                "Competition already exists"
        );

        CompetitionEntity c = mapToEntity(competitionDTO);
        unitOfWork.getCompetitionsRepository().persist(c);

        return mapToDTO(c);

    }

    @Transactional
    public CompetitionDTO delete(CompetitionDTO competitionDTO) {

        Map<String, Object> map = new HashMap<>();
        map.put("competitionName", competitionDTO.competitionName());
        map.put("competitionDate", competitionDTO.competitionDate());
        CompetitionEntity c = (CompetitionEntity) EntityValidator.validateExists(
                unitOfWork.getCompetitionsRepository(),
                map,
                "No such competition exists. Cannot delete."
        );

        unitOfWork.getCompetitionsRepository().delete(c);

        return mapToDTO(c);
    }


    // Mappers
    private CompetitionDTO mapToDTO(CompetitionEntity competitionEntity) {
        return new CompetitionDTO(competitionEntity.getCompetitionName(), competitionEntity.getCompetitionDate());
    }

    private CompetitionEntity mapToEntity(CompetitionDTO competitionDTO) {
        CompetitionEntity competitionEntity = new CompetitionEntity();
        competitionEntity.setCompetitionName(competitionDTO.competitionName());
        competitionEntity.setCompetitionDate(competitionDTO.competitionDate());
        return competitionEntity;
    }



}
