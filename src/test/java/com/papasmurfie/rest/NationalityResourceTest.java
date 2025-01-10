package com.papasmurfie.rest;

import com.papasmurfie.dto.EditNationalityDTO;
import com.papasmurfie.dto.NationalityDTO;
import com.papasmurfie.entities.NationalityEntity;
import com.papasmurfie.resources.NationalityResource;
import com.papasmurfie.uow.IUnitOfWork;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import java.util.List;


/**
 * Test class for the {@link com.papasmurfie.resources.NationalityResource} REST resource.
 * This class contains test cases to validate the functionality of the NationalityResource endpoints.
 */
@QuarkusTest
public class NationalityResourceTest {

    private final NationalityResource nationalityResource;
    private final IUnitOfWork unitOfWork;

    /**
     * Constructor for the test class.
     *
     * @param nationalityResource the {@link NationalityResource} to inject the resource being tested
     */
    public NationalityResourceTest(NationalityResource nationalityResource, IUnitOfWork unitOfWork) {
        this.nationalityResource = nationalityResource;
        this.unitOfWork = unitOfWork;
    }

    /**
     * Tests the retrieval of all nationalities from the resource.
     * Verifies that the returned list is not empty.
     */
    @Transactional
    @Test
    public void testGetAll(){
        List<NationalityDTO> dtos = nationalityResource.list();

        assert !dtos.isEmpty();
    }

    /**
     * Tests the retrieval of all nationalities from the resource by their name.
     * Verifies that the returned list is not empty.
     */
    @Transactional
    @Test
    public void testGetByName(){
        NationalityDTO dto = new NationalityDTO("Test Country");
        NationalityDTO createdDto = nationalityResource.create(dto);

        List<NationalityDTO> response = nationalityResource.get(dto.countryName()).stream().toList();

        assert !response.isEmpty();

        nationalityResource.delete(dto.countryName());
    }

    /**
     * Tests the creation of a nationality from the resource.
     * Verifies the competition is successfully created and performs cleanup by deleting the created competition.
     */
    @Transactional
    @Test
    public void testCreate(){
        NationalityDTO dto = new NationalityDTO("Test Country");

        NationalityDTO response = nationalityResource.create(dto);

        assert(response.countryName().equals(dto.countryName()));

        nationalityResource.delete(response.countryName());
    }

    /**
     * Tests the deletion of a nationality via the resource.
     * Verifies the competition is successfully deleted.
     */
    @Transactional
    @Test
    public void testDelete(){
        NationalityDTO dto = new NationalityDTO("Test Country");
        NationalityDTO createdDto = nationalityResource.create(dto);

        NationalityDTO response = nationalityResource.delete(dto.countryName());

        assert(response.countryName().equals(dto.countryName()));
    }

    /**
     * Tests the update of an existing nationality via the resource.
     * Verifies the nationality is updated correctly and performs cleanup by deleting the updated competition.
     */
    @Transactional
    @Test
    public void testUpdate(){
        NationalityDTO dto = new NationalityDTO("Test Country");
        NationalityDTO createdDto = nationalityResource.create(dto);
        EditNationalityDTO editNationalityDTO = new EditNationalityDTO(
                dto.countryName(),
                "Updated Country"
        );
        NationalityDTO updated = nationalityResource.update(editNationalityDTO);
        assert(updated.countryName().equals(editNationalityDTO.newNationalityName()));

        nationalityResource.delete(editNationalityDTO.newNationalityName());
    }


    @Test
    public void testGetAllREST() {
        given()
                .when().get("/api/v1/nationality")
                .then()
                .statusCode(200)
                .body(is(not(emptyString())));
    }

    @Test
    public void testCreateREST() {
        NationalityDTO dto = new NationalityDTO("Test Country");

        given()
                .contentType("application/json")
                .body(dto)
                .when().post("/api/v1/nationality")
                .then()
                .statusCode(200);

        nationalityResource.delete(dto.countryName());
    }

//    @Transactional
//    @Test
//    public void testDeleteREST() {
//
//
//        NationalityEntity e = new NationalityEntity();
//        e.setCountryName("Test Country");
//        unitOfWork.getNationalitiesRepository().persist(e);
//
//
//        given()
//                .when().delete("/nationality/Test")
//                .then()
//                .statusCode(200);
//    }



}
