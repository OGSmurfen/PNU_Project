package com.papasmurfie.resources;

import com.papasmurfie.dto.CompetitorDTO;
import com.papasmurfie.dto.EditCompetitorDTO;
import com.papasmurfie.services.CompetitorsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;

/**
 * REST resource for managing {@link CompetitorDTO} objects.
 * <p>
 * This class provides endpoints for creating, reading, updating, and deleting competitor information.
 * It delegates the business logic to the {@link CompetitorsService} class.
 * </p>
 * <p>
 * The resource is exposed at the base path "/api/v1/competitor" and supports the following HTTP methods:
 * <ul>
 *     <li><b>POST:</b> Save a new competitor.</li>
 *     <li><b>GET:</b> Retrieve all competitors.</li>
 *     <li><b>PUT:</b> Update an existing competitor.</li>
 *     <li><b>DELETE:</b> Delete a competitor.</li>
 * </ul>
 * </p>
 */
@ApplicationScoped
@Path("api/v1/competitor")
public class CompetitorResource {

    private final CompetitorsService competitorsService;

    /**
     * Constructor used for injecting dependencies.
     *
     * @param competitorsService the service that handles business logic for competitors, injected by the DI container
     */
    public CompetitorResource(CompetitorsService competitorsService) {
        this.competitorsService = competitorsService;
    }

    /**
     * Creates a new competitor.
     *
     * @param competitorDTO the competitor data transfer object containing the details of the competitor to be saved
     * @return the created {@link CompetitorDTO}
     */
    @Operation(
            summary = "Create a new competitor",
            description = "This endpoint allows you to create a new competitor by providing the necessary details in the request body."
    )
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public CompetitorDTO create(CompetitorDTO competitorDTO) {
        return competitorsService.save(competitorDTO);
    }

    /**
     * Retrieves all competitors.
     *
     * @return a list of all {@link CompetitorDTO} objects
     */
    @Operation(
            summary = "Get all competitors",
            description = "This endpoint retrieves a list of all competitors currently in the system."
    )
    @GET
    public List<CompetitorDTO> list(){
        return competitorsService.getAll();
    }

    /**
     * Deletes an existing competitor.
     *
     * @param competitorDTO the competitor data transfer object containing the details of the competitor to be deleted
     * @return the deleted {@link CompetitorDTO}
     */
    @Operation(
            summary = "Delete a competitor",
            description = "This endpoint deletes a competitor by the provided competitor details."
    )
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public CompetitorDTO delete(CompetitorDTO competitorDTO) {
        return competitorsService.delete(competitorDTO);
    }

    /**
     * Updates an existing competitor.
     *
     * @param competitorDTO the data transfer object containing the updated competitor details
     * @return the updated {@link CompetitorDTO}
     */
    @Operation(
            summary = "Update an existing competitor",
            description = "This endpoint updates an existing competitor with the provided updated details."
    )
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public CompetitorDTO update(EditCompetitorDTO competitorDTO) {
        return competitorsService.update(competitorDTO);
    }
}
