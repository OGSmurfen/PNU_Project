package com.papasmurfie.services;

import com.papasmurfie.dto.CompetitorDTO;
import com.papasmurfie.dto.EditCompetitorDTO;
import com.papasmurfie.entities.CompetitorEntity;
import com.papasmurfie.entities.NationalityEntity;
import com.papasmurfie.uow.IUnitOfWork;
import com.papasmurfie.utility.EntityValidator;
import com.papasmurfie.utility.ErrorResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling the business logic of competitors, such as saving, deleting, updating,
 * and retrieving competitor data from the repository.
 * <p>
 * This service class is annotated as {@link ApplicationScoped}, which makes it available as a CDI (Contexts and
 * Dependency Injection) bean in the application.
 */
@ApplicationScoped
public class CompetitorsService {

    private final IUnitOfWork unitOfWork;

    /**
     * Constructs a {@link CompetitorsService} with the provided unit of work.
     *
     * @param unitOfWork The unit of work used to interact with repositories.
     */
    public CompetitorsService(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }


    /**
     * Retrieves all competitors from the repository.
     * <p>
     * Converts {@link CompetitorEntity} to {@link CompetitorDTO} before returning the list.
     *
     * @return A list of {@link CompetitorDTO} representing all competitors.
     */
    @Transactional
    public List<CompetitorDTO> getAll() {
        return unitOfWork.getCompetitorsRepository()
                .findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Saves a new competitor to the repository.
     * <p>
     * Validates that the competitor's phone and email are unique before saving. It also validates the existence of the
     * nationalities associated with the competitor.
     *
     * @param competitorDTO The {@link CompetitorDTO} containing the competitor's data.
     * @return The {@link CompetitorDTO} of the saved competitor.
     * @throws WebApplicationException If a competitor with the same phone or email already exists or if a nationality
     *                                 does not exist.
     */
    @Transactional
    public CompetitorDTO save(CompetitorDTO competitorDTO) {
        CompetitorEntity competitor = mapToEntity(competitorDTO);

        EntityValidator.validateUnique(
                unitOfWork.getCompetitorsRepository(),
                "phone",
                competitor.getPhone(),
                "Competitor with phone number '" + competitor.getPhone() + "' already exists"
        );
        EntityValidator.validateUnique(
                unitOfWork.getCompetitorsRepository(),
                "email",
                competitor.getEmail(),
                "Competitor with email '" + competitor.getEmail() + "' already exists"
        );
        for(String nationality : competitorDTO.nationalities()) {
            NationalityEntity entity = (NationalityEntity) EntityValidator.validateExists(
                    unitOfWork.getNationalitiesRepository(),
                    "countryName",
                    nationality,
                    "No such nationality: '" + nationality + "'"
                    );
        }

        unitOfWork.getCompetitorsRepository().persist(competitor);
        return competitorDTO;
    }

    /**
     * Deletes a competitor based on the provided {@link CompetitorDTO}.
     * <p>
     * Validates that the competitor exists before deleting.
     *
     * @param competitorDTO The {@link CompetitorDTO} containing the competitor's data to delete.
     * @return The {@link CompetitorDTO} of the deleted competitor.
     * @throws WebApplicationException If the competitor with the provided phone does not exist.
     */
    @Transactional
    public CompetitorDTO delete(CompetitorDTO competitorDTO) {
        CompetitorEntity competitor = (CompetitorEntity) EntityValidator.validateExists(
                unitOfWork.getCompetitorsRepository(),
                "phone",
                competitorDTO.mobilePhone(),
                "Incorrect information for deletion"
        );

        unitOfWork.getCompetitorsRepository().delete(competitor);
        return mapToDto(competitor);
    }

    /**
     * Updates an existing competitor with new data from the provided {@link EditCompetitorDTO}.
     * <p>
     * Validates that the competitor exists and that the current competitor's details match the ones provided in the
     * DTO. It also validates the existence of the new nationalities.
     *
     * @param competitorDTO The {@link EditCompetitorDTO} containing the updated competitor data.
     * @return The {@link CompetitorDTO} of the updated competitor.
     * @throws WebApplicationException If the competitor does not exist or if the provided details do not match.
     */
    @Transactional
    public CompetitorDTO update(EditCompetitorDTO competitorDTO) {
        CompetitorEntity competitor = (CompetitorEntity) EntityValidator.validateExists(
                unitOfWork.getCompetitorsRepository(),
                "phone",
                competitorDTO.mobilePhone(),
                "Cannot update competitor that does not exist"
        );

        if(
                !competitor.getCompetitorFirstName().equals(competitorDTO.firstName()) ||
                !competitor.getCompetitorLastName().equals(competitorDTO.lastName()) ||
                !competitor.getCompetitorMiddleName().equals(competitorDTO.middleName()) ||
                !competitor.getEmail().equals(competitorDTO.email())
                )
        {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse(
                                    404,
                                    "Not found",
                                    "Some or all of the properties of the competitor do not match"
                            ))
                            .type("application/json")
                            .build()
            );
        }

        List<NationalityEntity> nats = new ArrayList<>();
        for(String nationality : competitorDTO.newNationalities()) {
            NationalityEntity entity = (NationalityEntity) EntityValidator.validateExists(
                    unitOfWork.getNationalitiesRepository(),
                    "countryName",
                    nationality,
                    "No such nationality: '" + nationality + "'"
            );
            nats.add(entity);
        }

        competitor.setCompetitorFirstName(competitorDTO.newFirstName());
        competitor.setCompetitorMiddleName(competitorDTO.newMiddleName());
        competitor.setCompetitorLastName(competitorDTO.newLastName());
        competitor.setPhone(competitorDTO.newMobilePhone());
        competitor.setEmail(competitorDTO.newEmail());
        competitor.setNationalities(nats);

        return mapToDto(competitor);
    }

    // Mappers

    /**
     * Converts a {@link CompetitorEntity} to a {@link CompetitorDTO}.
     *
     * @param competitorEntity The entity to map.
     * @return The corresponding {@link CompetitorDTO}.
     */
    private CompetitorDTO mapToDto(CompetitorEntity competitorEntity) {
        List<NationalityEntity> nationalities = competitorEntity.getNationalities();
        List<String>nats = new ArrayList<>();

        for (NationalityEntity nationalityEntity : nationalities) {
            nats.add(nationalityEntity.getCountryName());

        }

        return new CompetitorDTO(
                competitorEntity.getCompetitorFirstName(),
                competitorEntity.getCompetitorMiddleName(),
                competitorEntity.getCompetitorLastName(),
                competitorEntity.getPhone(),
                competitorEntity.getEmail(),
                nats
        );
    }

    /**
     * Converts a {@link CompetitorDTO} to a {@link CompetitorEntity}.
     *
     * @param competitorDTO The DTO to map.
     * @return The corresponding {@link CompetitorEntity}.
     */
    private CompetitorEntity mapToEntity(CompetitorDTO competitorDTO) {
        CompetitorEntity competitor = new CompetitorEntity();
        competitor.setCompetitorFirstName(competitorDTO.firstName());
        competitor.setCompetitorMiddleName(competitorDTO.middleName());
        competitor.setCompetitorLastName(competitorDTO.lastName());
        competitor.setPhone(competitorDTO.mobilePhone());
        competitor.setEmail(competitorDTO.email());

        List<NationalityEntity> nationalities = new ArrayList<>();
        for (String countryName : competitorDTO.nationalities()) {
            NationalityEntity nationality = unitOfWork.getNationalitiesRepository()
                    .find("countryName", countryName)
                    .firstResult();
            if(nationality != null) {
                nationalities.add(nationality);
            }
        }

        competitor.setNationalities(nationalities);
        return competitor;

    }
}
