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

/**
 * Service class responsible for handling the business logic of competitions, such as saving, deleting, updating,
 * and retrieving competition data from the database.
 * <p>
 * This service class is annotated as {@link ApplicationScoped}, which makes it available as a CDI (Contexts and
 * Dependency Injection) bean in the application.
 */
@ApplicationScoped
public class CompetitionsService {

    private final IUnitOfWork unitOfWork;

    /**
     * Constructs a {@link CompetitionsService} with the provided unit of work.
     *
     * @param unitOfWork The unit of work used to interact with repositories.
     */
    public CompetitionsService(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    /**
     * Saves a new competition to the repository.
     * <p>
     * Validates that the competition with the given name and date doesn't already exist before saving.
     *
     * @param competitionDTO The {@link CompetitionDTO} containing the competition data.
     * @return The {@link CompetitionDTO} of the saved competition.
     * @throws WebApplicationException If a competition with the same name and date already exists.
     */
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

    /**
     * Deletes a competition based on the provided {@link CompetitionDTO}.
     * <p>
     * Validates that the competition exists before deleting.
     *
     * @param competitionDTO The {@link CompetitionDTO} containing the competition data to delete.
     * @return The {@link CompetitionDTO} of the deleted competition.
     * @throws WebApplicationException If the competition does not exist.
     */
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

    /**
     * Retrieves all competitions from the repository.
     * <p>
     * Throws an exception if no competitions are found.
     *
     * @return A list of {@link CompetitionDTO} representing all competitions.
     * @throws WebApplicationException If no competitions are found.
     */
    @Transactional
    public List<CompetitionDTO> getAll() {

        List<CompetitionDTO> competitions = unitOfWork.getCompetitionsRepository()
                .findAll().stream().map(this::mapToDTO).toList();

        EntityValidator.throwNotFoundException(competitions);

        return competitions;
    }

    /**
     * Retrieves competitions by their name from the repository.
     * <p>
     * Throws an exception if no competitions with the provided name are found.
     *
     * @param name The competition name to search for.
     * @return A list of {@link CompetitionDTO} matching the provided name.
     * @throws WebApplicationException If no competitions with the given name are found.
     */
    @Transactional
    public List<CompetitionDTO> getCompetitionsByName(String name) {
        name = "%" + name.toLowerCase() + "%";

        List<CompetitionDTO> competitions = unitOfWork.getCompetitionsRepository()
                .find("LOWER(competitionName) LIKE ?1", name)
                .stream().map(this::mapToDTO).toList();

        EntityValidator.throwNotFoundException(competitions);

        return competitions;
    }

    /**
     * Retrieves competitions by their date from the repository.
     * <p>
     * Throws an exception if no competitions with the provided date are found or if the date format is invalid.
     *
     * @param dateString The date of the competition in {@code yyyy-MM-dd} format.
     * @return A list of {@link CompetitionDTO} matching the provided date.
     * @throws WebApplicationException If no competitions with the given date are found or if the date format is invalid.
     */
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

    /**
     * Retrieves competitions between two dates from the repository.
     * <p>
     * Throws an exception if no competitions in the specified date range are found or if the date format is invalid.
     *
     * @param dateBeginString The start date of the range in {@code yyyy-MM-dd} format.
     * @param dateEndString   The end date of the range in {@code yyyy-MM-dd} format.
     * @return A list of {@link CompetitionDTO} matching the date range.
     * @throws WebApplicationException If no competitions in the date range are found or if the date format is invalid.
     */
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

    /**
     * Updates an existing competition with new values.
     * <p>
     * Throws an exception if the competition is not found or if the update fails.
     *
     * @param editCompetitionDTO The DTO containing the competition's updated data.
     * @return The {@link CompetitionDTO} representing the updated competition.
     * @throws WebApplicationException If the competition is not found.
     */
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

    /**
     * Maps a {@link CompetitionEntity} to a {@link CompetitionDTO}.
     *
     * @param competitionEntity The entity to map.
     * @return The corresponding {@link CompetitionDTO}.
     */
    private CompetitionDTO mapToDTO(CompetitionEntity competitionEntity) {
        return new CompetitionDTO(competitionEntity.getCompetitionName(), competitionEntity.getCompetitionDate());
    }

    /**
     * Maps a {@link CompetitionDTO} to a {@link CompetitionEntity}.
     *
     * @param competitionDTO The DTO to map.
     * @return The corresponding {@link CompetitionEntity}.
     */
    private CompetitionEntity mapToEntity(CompetitionDTO competitionDTO) {
        CompetitionEntity competitionEntity = new CompetitionEntity();
        competitionEntity.setCompetitionName(competitionDTO.competitionName());
        competitionEntity.setCompetitionDate(competitionDTO.competitionDate());
        return competitionEntity;
    }



}
