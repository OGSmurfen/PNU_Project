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

@ApplicationScoped
public class NationalitiesService {
    private final IUnitOfWork unitOfWork;

    public NationalitiesService(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }


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

    @Transactional
    public NationalityDTO save(NationalityDTO nationalityDto) {

        EntityValidator.validateUnique(unitOfWork.getNationalitiesRepository(),
                "countryName", nationalityDto.countryName(),
                "The country '"+nationalityDto.countryName()+"' already exists.");

        NationalityEntity nationalityEntity = mapToEntity(nationalityDto);
        unitOfWork.getNationalitiesRepository().persist(nationalityEntity);
        return nationalityDto;
    }

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
    private NationalityEntity mapToEntity(NationalityDTO newNationality) {
        NationalityEntity newNationalityEntity = new NationalityEntity();
        newNationalityEntity.setCountryName(newNationality.countryName());
        return newNationalityEntity;
    }
    private NationalityDTO mapToDto(NationalityEntity n) {
        return new NationalityDTO(
                n.getCountryName()
        );
    }
}
