package com.papasmurfie.rest;


import com.papasmurfie.dto.CompetitorDTO;
import com.papasmurfie.dto.EditCompetitorDTO;
import com.papasmurfie.dto.NationalityDTO;
import com.papasmurfie.entities.NationalityEntity;
import com.papasmurfie.resources.CompetitorResource;
import com.papasmurfie.resources.NationalityResource;
import com.papasmurfie.uow.IUnitOfWork;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import uow.UnitOfWorkTest;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;


/**
 * Test class for the {@link com.papasmurfie.resources.CompetitorResource} REST resource.
 * This class contains test cases to validate the functionality of the CompetitorResource endpoints.
 */
@QuarkusTest
public class CompetitorsResourceTest {

    private final CompetitorResource competitorResource;
    private final IUnitOfWork unitOfWork;

    public CompetitorsResourceTest(CompetitorResource competitorResource, IUnitOfWork unitOfWork) {
        this.competitorResource = competitorResource;
        this.unitOfWork = unitOfWork;
    }


    /**
     * Tests the creation of a competitor from the resource.
     * Verifies the competitor is successfully created and performs cleanup by deleting the created competitor.
     */
    @Transactional
    @Test
    public void testCreate(){
        List<NationalityEntity> nationalityEntities = unitOfWork.getNationalitiesRepository().findAll().stream().toList();
        List<String> nationalities = new LinkedList<>();
        nationalities.add(nationalityEntities.getFirst().getCountryName());

        CompetitorDTO dto = new CompetitorDTO(
                "Test",
                "Test",
                "Test",
                "0123456789",
                "test@test.test",
                nationalities
        );

        CompetitorDTO created = competitorResource.create(dto);

        assert (Objects.equals(created.firstName(), "Test")
                && created.middleName().equals("Test")
                && Objects.equals(created.lastName(), "Test")
                && Objects.equals(created.email(), "test@test.test")
                && created.mobilePhone().equals("0123456789"));

        competitorResource.delete(created);
    }


    /**
     * Tests the deletion of a competitor via the resource.
     * Verifies the competitor is successfully deleted.
     */
    @Transactional
    @Test
    public void testDelete(){
        List<NationalityEntity> nationalityEntities = unitOfWork.getNationalitiesRepository().findAll().stream().toList();
        List<String> nationalities = new LinkedList<>();
        nationalities.add(nationalityEntities.getFirst().getCountryName());

        CompetitorDTO dto = new CompetitorDTO(
                "Test",
                "Test",
                "Test",
                "0123456789",
                "test@test.test",
                nationalities
        );

        CompetitorDTO created = competitorResource.create(dto);

        CompetitorDTO deleted = competitorResource.delete(created);

        assert (Objects.equals(deleted.firstName(), "Test")
                && deleted.middleName().equals("Test")
                && Objects.equals(deleted.lastName(), "Test")
                && Objects.equals(deleted.email(), "test@test.test")
                && deleted.mobilePhone().equals("0123456789"));
    }

    /**
     * Tests the update of an existing competitor via the resource.
     * Verifies the competitor is updated correctly and performs cleanup by deleting the updated competitor.
     */
    @Transactional
    @Test
    public void testUpdate(){
        List<NationalityEntity> nationalityEntities = unitOfWork.getNationalitiesRepository().findAll().stream().toList();
        List<String> nationalities = new LinkedList<>();
        nationalities.add(nationalityEntities.getFirst().getCountryName());

        CompetitorDTO dto = new CompetitorDTO(
                "Test",
                "Test",
                "Test",
                "0123456789",
                "test@test.test",
                nationalities
        );

        CompetitorDTO created = competitorResource.create(dto);

        EditCompetitorDTO editCompetitorDTO = new EditCompetitorDTO(
                dto.firstName(),
                dto.middleName(),
                dto.lastName(),
                dto.mobilePhone(),
                dto.email(),
                dto.nationalities(),
                "Updated",
                "Updated",
                "Updated",
                "0987654321",
                "updated@updated.updated",
                dto.nationalities()
        );

        CompetitorDTO updated = competitorResource.update(editCompetitorDTO);



        assert (Objects.equals(updated.firstName(), "Updated")
                && updated.middleName().equals("Updated")
                && Objects.equals(updated.lastName(), "Updated")
                && Objects.equals(updated.email(), "updated@updated.updated")
                && updated.mobilePhone().equals("0987654321"));

        competitorResource.delete(updated);
    }



}
