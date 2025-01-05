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

@ApplicationScoped
public class CompetitorsService {

    private final IUnitOfWork unitOfWork;

    public CompetitorsService(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }


    @Transactional
    public List<CompetitorDTO> getAll() {
        return unitOfWork.getCompetitorsRepository()
                .findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

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
