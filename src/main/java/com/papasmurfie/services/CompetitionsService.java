package com.papasmurfie.services;

import com.papasmurfie.dto.CompetitionDTO;
import com.papasmurfie.dto.EditCompetitionDTO;
import com.papasmurfie.entities.CompetitionEntity;
import com.papasmurfie.uow.IUnitOfWork;
import com.papasmurfie.utility.EntityValidator;
import com.papasmurfie.utility.ErrorResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
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

    @Transactional
    public List<CompetitionDTO> getAll() {

        List<CompetitionDTO> competitions = unitOfWork.getCompetitionsRepository()
                .findAll().stream().map(this::mapToDTO).toList();

        EntityValidator.throwNotFoundException(competitions);

        return competitions;
    }

    @Transactional
    public List<CompetitionDTO> getCompetitionsByName(String name) {
        name = "%" + name.toLowerCase() + "%";

        List<CompetitionDTO> competitions = unitOfWork.getCompetitionsRepository()
                .find("LOWER(competitionName) LIKE ?1", name)
                .stream().map(this::mapToDTO).toList();

        EntityValidator.throwNotFoundException(competitions);

        return competitions;
    }

    @Transactional
    public List<CompetitionDTO> getCompetitionsByDate(String dateString) {
        LocalDate date;

        try {
            date = LocalDate.parse(dateString);
        } catch (DateTimeParseException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(new ErrorResponse(
                                    400,
                                    "Bad Request",
                                    "Invalid date format. Expected format is yyyy-MM-dd."
                            ))
                            .type("application/json")
                            .build()
            );
        }

        List<CompetitionDTO> competitions = unitOfWork.getCompetitionsRepository()
                .find("competitionDate", date)
                .stream().map(this::mapToDTO).toList();

        EntityValidator.throwNotFoundException(competitions);

        return competitions;
    }
    @Transactional
    public List<CompetitionDTO> getCompetitionsBetweenDates(String dateBeginString, String dateEndString) {
        LocalDate dateBegin;
        LocalDate dateEnd;

        try {
            dateBegin = LocalDate.parse(dateBeginString);
            dateEnd = LocalDate.parse(dateEndString);
        } catch (DateTimeParseException e) {
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(new ErrorResponse(
                                    400,
                                    "Bad Request",
                                    "Invalid date format. Expected format is yyyy-MM-dd."
                            ))
                            .type("application/json")
                            .build()
            );
        }

        List<CompetitionDTO> competitions = unitOfWork.getCompetitionsRepository()
                .find("competitionDate BETWEEN ?1 AND ?2", dateBegin, dateEnd)
                .stream().map(this::mapToDTO).toList();

        EntityValidator.throwNotFoundException(competitions);

        return competitions;
    }

    @Transactional
    public CompetitionDTO update(EditCompetitionDTO editCompetitionDTO) {
        CompetitionEntity competitionEntity = unitOfWork.getCompetitionsRepository()
                .find("competitionName LIKE ?1 AND competitionDate = ?2",
                        editCompetitionDTO.competitionName(),
                        editCompetitionDTO.competitionDate()).firstResult();

        EntityValidator.throwNotFoundException(competitionEntity);

        competitionEntity.setCompetitionName(editCompetitionDTO.newCompetitionName());
        competitionEntity.setCompetitionDate(editCompetitionDTO.newCompetitionDate());

        return new CompetitionDTO(
          editCompetitionDTO.newCompetitionName(),
          editCompetitionDTO.newCompetitionDate()
        );

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
