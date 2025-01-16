package com.papasmurfie.rest;

import com.papasmurfie.dto.EditNationalityDTO;
import com.papasmurfie.dto.NationalityDTO;
import com.papasmurfie.resources.NationalityResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


/**
 * Test class for the {@link com.papasmurfie.resources.NationalityResource} REST resource.
 * This class contains test cases to validate the functionality of the NationalityResource endpoints.
 */
@QuarkusTest
public class NationalityResourceTest {

    private final NationalityResource nationalityResource;

    /**
     * Constructor for the test class.
     *
     * @param nationalityResource the {@link NationalityResource} to inject the resource being tested
     */
    public NationalityResourceTest(NationalityResource nationalityResource) {
        this.nationalityResource = nationalityResource;
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
     * Verifies the nationality is successfully deleted.
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


    /**
     * Tests the retrieve of all nationalities from the resource via an HTTP GET action.
     * Verifies the competition is successfully created and performs cleanup by deleting the created competition.
     */
    @Test
    public void testGetAllREST() {
        given()
                .when().get("/api/v1/nationality")
                .then()
                .statusCode(200)
                .body(is(not(emptyString())));
    }

    /**
     * Tests the creation of a nationality from the resource via an HTTP POST action.
     * Verifies the competition is successfully created and performs cleanup by deleting the created competition.
     */
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


//    @Test
//    public void testDeleteREST() {
//
//
//        List<NationalityDTO> dtos = nationalityResource.list();
//        String name = dtos.get(dtos.size()-1).countryName();
//
//        assert !dtos.isEmpty() : "No records found in the database!";
//
//        given()
//                .when().delete(String.format("/api/v1/nationality/%s", name.replace(" ", "%20")))
//                .then()
//                .statusCode(200);
//    }



}
