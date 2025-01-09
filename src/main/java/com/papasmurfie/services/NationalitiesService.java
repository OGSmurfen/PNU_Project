package com.papasmurfie.services;


import com.papasmurfie.dto.EditNationalityDTO;
import com.papasmurfie.dto.NationalityDTO;
import com.papasmurfie.entities.NationalityEntity;
import com.papasmurfie.uow.IUnitOfWork;
import com.papasmurfie.utility.EntityValidator;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for handling business logic related to nationalities.
 * <p>
 * This service includes functionality to retrieve, add, update, and delete nationalities from the repository.
 * It is marked as {@link ApplicationScoped} to allow for CDI (Contexts and Dependency Injection) in the application.
 */
@ApplicationScoped
public class NationalitiesService {
    private final IUnitOfWork unitOfWork;

    /**
     * Constructs a {@link NationalitiesService} with the provided unit of work.
     *
     * @param unitOfWork The unit of work to interact with the repository.
     */
    public NationalitiesService(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    /**
     * Retrieves all nationalities from the repository.
     * <p>
     * If no nationalities are found, a {@link NotFoundException} is thrown.
     *
     * @return A list of {@link NationalityDTO} representing all nationalities.
     * @throws NotFoundException If no nationalities are found.
     */
    public List<NationalityDTO> getAll() {
        List<NationalityDTO> nationalities = unitOfWork.getNationalitiesRepository().findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        if(nationalities.isEmpty()) {
            throw new NotFoundException();
        }

        return nationalities;
    }

    /**
     * Retrieves nationalities by partial name.
     * <p>
     * Searches for nationalities whose country name matches the given partial name.
     *
     * @param countryName The partial name of the country to search for.
     * @return A list of {@link NationalityDTO} that match the partial name.
     * @throws NotFoundException If no matching nationalities are found.
     */
    public List<NationalityDTO> getNationalitiesByPartialName(String countryName) {
        countryName = "%" + countryName + "%";

//        List<NationalityDTO> nationalities = unitOfWork.getNationalitiesRepository()
//                .find("LOWER(countryName) like ?1", countryName)
//                .stream()
//                .map(this::mapToDto)
//                .toList();
//        if(nationalities.isEmpty()) {
//            throw new NotFoundException();
//        }

        List<NationalityEntity> nationalities = (List<NationalityEntity>) EntityValidator.validateExist(
                unitOfWork.getNationalitiesRepository(),
                "LOWER(countryName) like ?1",
                countryName.toLowerCase(),
                "Cannot find entities"
        );

        return nationalities.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    /**
     * Saves a new nationality to the repository.
     * <p>
     * Validates that the nationality does not already exist before saving it.
     *
     * @param nationalityDto The {@link NationalityDTO} containing the data to save.
     * @return The saved {@link NationalityDTO}.
     * @throws IllegalArgumentException If a nationality with the same name already exists.
     */
    @Transactional
    public NationalityDTO save(NationalityDTO nationalityDto) {

        EntityValidator.validateUnique(unitOfWork.getNationalitiesRepository(),
                "countryName", nationalityDto.countryName(),
                "The country '"+nationalityDto.countryName()+"' already exists.");

        NationalityEntity nationalityEntity = mapToEntity(nationalityDto);
        unitOfWork.getNationalitiesRepository().persist(nationalityEntity);
        return nationalityDto;
    }

    /**
     * Deletes an existing nationality based on the provided country name.
     * <p>
     * Validates that the nationality exists before performing the deletion.
     *
     * @param countryName The name of the country to delete.
     * @return The {@link NationalityDTO} of the deleted nationality.
     * @throws NotFoundException If the nationality with the given name does not exist.
     */
    @Transactional
    public NationalityDTO delete(String countryName) {

        NationalityEntity n = (NationalityEntity) EntityValidator.validateExists(
                unitOfWork.getNationalitiesRepository(),
                "countryName",
                countryName,
                "The country '"+countryName+"' does not exist and therefore cannot be deleted.");

        unitOfWork.getNationalitiesRepository().delete(n);
        return mapToDto(n);
    }

    /**
     * Updates an existing nationality with new data.
     * <p>
     * Validates that the nationality exists before applying updates.
     *
     * @param editNationalityDto The {@link EditNationalityDTO} containing the new data for the nationality.
     * @return The updated {@link NationalityDTO}.
     * @throws NotFoundException If the nationality with the current name does not exist.
     */
    @Transactional
    public NationalityDTO update(EditNationalityDTO editNationalityDto) {
        NationalityEntity n = unitOfWork.getNationalitiesRepository()
                .find("countryName", editNationalityDto.currentNationalityName())
                .firstResult();

        if (n == null) {
            throw new NotFoundException("Nationality not found: " + editNationalityDto.currentNationalityName());
        }

        if(!unitOfWork.getNationalitiesRepository().isPersistent(n)){
            throw new IllegalStateException("Entity is not persistent but should be.");
        }

        n.setCountryName(editNationalityDto.newNationalityName());
        return mapToDto(n);
    }



    // Mappers
    /**
     * Converts a {@link NationalityDTO} to a {@link NationalityEntity}.
     *
     * @param newNationality The {@link NationalityDTO} to convert.
     * @return The corresponding {@link NationalityEntity}.
     */
    private NationalityEntity mapToEntity(NationalityDTO newNationality) {
        NationalityEntity newNationalityEntity = new NationalityEntity();
        newNationalityEntity.setCountryName(newNationality.countryName());
        return newNationalityEntity;
    }

    /**
     * Converts a {@link NationalityEntity} to a {@link NationalityDTO}.
     *
     * @param n The {@link NationalityEntity} to convert.
     * @return The corresponding {@link NationalityDTO}.
     */
    private NationalityDTO mapToDto(NationalityEntity n) {
        return new NationalityDTO(
                n.getCountryName()
        );
    }
}
