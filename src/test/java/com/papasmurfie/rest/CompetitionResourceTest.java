package com.papasmurfie.rest;

import com.papasmurfie.dto.CompetitionDTO;
import com.papasmurfie.dto.EditCompetitionDTO;
import com.papasmurfie.resources.CompetitionResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Test class for the {@link CompetitionResource} REST resource.
 * This class contains test cases to validate the functionality of the CompetitionResource endpoints.
 */
@QuarkusTest
public class CompetitionResourceTest {

    private final CompetitionResource competitionResource;

    /**
     * Constructor for the test class.
     *
     * @param competitionResource the {@link CompetitionResource} to inject the resource being tested
     */
    public CompetitionResourceTest(CompetitionResource competitionResource) {
        this.competitionResource = competitionResource;
    }

    /**
     * Tests the retrieval of all competitions from the resource.
     * Verifies that the returned list is not empty.
     */
    @Transactional
    @Test
    public void testGetAllCompetitions(){
        List<CompetitionDTO> dtos = competitionResource.getAll();

        assert !dtos.isEmpty();
    }

    /**
     * Tests the creation of a new competition via the resource.
     * Verifies the competition is successfully created and performs cleanup by deleting the created competition.
     */
    @Transactional
    @Test
    public void testCreateCompetition(){
        CompetitionDTO dto = new CompetitionDTO(
                "Test Competition",
                LocalDate.now()
        );
        Response response = competitionResource.create(dto);

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertNotNull(response.getEntity());

        competitionResource.delete(dto);
    }

    /**
     * Tests the update of an existing competition via the resource.
     * Verifies the competition is updated correctly and performs cleanup by deleting the updated competition.
     */
    @Transactional
    @Test
    public void testEditCompetition(){
        CompetitionDTO createDto = new CompetitionDTO(
                "Test Competition",
                LocalDate.now()
        );
        competitionResource.create(createDto);

        EditCompetitionDTO editCompetitionDTO = new EditCompetitionDTO(
                "Test Competition",
                LocalDate.now(),
                "Updated Competition",
                LocalDate.now()
        );
        CompetitionDTO response = competitionResource.update(editCompetitionDTO);

        assertEquals(editCompetitionDTO.newCompetitionName(), response.competitionName());

        competitionResource.delete(response);
    }

    /**
     * Tests the deletion of a competition via the resource.
     * Verifies the competition is successfully deleted.
     */
    @Transactional
    @Test
    void testDeleteCompetition() {
        CompetitionDTO dto = new CompetitionDTO("Competition to delete", LocalDate.now());
        competitionResource.create(dto);

        CompetitionDTO response = competitionResource.delete(dto);

        assertEquals(dto, response);
    }

    /**
     * Tests retrieving a competition by its name via the resource.
     * Verifies the returned list contains exactly one matching competition.
     */
    @Transactional
    @Test
    public void testGetCompetitionByName() {
        CompetitionDTO dto = new CompetitionDTO("Test Competition XD", LocalDate.now());
        competitionResource.create(dto);

        List<CompetitionDTO> c = competitionResource.getByName(dto.competitionName());

        assertEquals(1, c.size());

        competitionResource.delete(dto);
    }

    /**
     * Tests retrieving competitions by a specific date via the resource.
     * Verifies the returned list contains the expected competition.
     */
    @Transactional
    @Test
    public void testGetCompetitionByDate() {
        CompetitionDTO dto = new CompetitionDTO("Test Competition XD", LocalDate.now());
        competitionResource.create(dto);

        List<CompetitionDTO> c = competitionResource.getByDate(dto.competitionDate().toString());

        assertEquals(1, c.size());

        competitionResource.delete(dto);
    }

    /**
     * Tests retrieving competitions between two dates via the resource.
     * Verifies the returned list is not empty and includes the expected competition.
     */
    @Transactional
    @Test
    public void testGetCompetitionBetweenTwoDates() {
        CompetitionDTO dto = new CompetitionDTO("Test Competition XD", LocalDate.of(2025, 1, 10));
        competitionResource.create(dto);

        List<CompetitionDTO> c = competitionResource.getBetweenTwoDates("2025-01-01", "2026-01-01");

        assert !c.isEmpty();

        competitionResource.delete(dto);
    }


}
