package com.papasmurfie.resources;

import com.papasmurfie.dto.CompetitionDTO;
import com.papasmurfie.dto.EditCompetitionDTO;
import com.papasmurfie.services.CompetitionsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import java.util.List;

/**
 * REST resource for managing {@link CompetitionDTO} objects.
 * <p>
 * This class provides endpoints for creating, reading, updating, and deleting competition information.
 * It delegates the business logic to the {@link CompetitionsService} class.
 * </p>
 * <p>
 * The resource is exposed at the base path "/competition" and supports the following HTTP methods:
 * <ul>
 *     <li><b>POST:</b> Create a new competition.</li>
 *     <li><b>GET:</b> Retrieve all competitions, or search competitions by name or date.</li>
 *     <li><b>PUT:</b> Update an existing competition.</li>
 *     <li><b>DELETE:</b> Delete a competition.</li>
 * </ul>
 * </p>
 */
@ApplicationScoped
@Path("/competition")
public class CompetitionResource {

    private final CompetitionsService competitionsService;

    /**
     * Constructor used for injecting dependencies.
     *
     * @param competitionsService the service that handles business logic for competitions, injected by the DI container
     */
    public CompetitionResource(CompetitionsService competitionsService) {
        this.competitionsService = competitionsService;
    }


    /**
     * Creates a new competition.
     *
     * @param competitionDTO the competition data transfer object containing the details of the competition to be created
     * @return the created {@link CompetitionDTO}
     */
    @Operation(
            summary = "Create a new competition",
            description = "This endpoint creates a new competition based on the provided competition data."
    )
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "Competition created successfully"),
            @APIResponse(responseCode = "400", description = "Bad request, invalid input"),
            @APIResponse(responseCode = "409", description = "Duplicate entry"),
            @APIResponse(responseCode = "500", description = "Internal server error")
    })
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CompetitionDTO competitionDTO) {
        return competitionsService.save(competitionDTO);
    }

    /**
     * Deletes a competition.
     *
     * @param competitionDTO the competition data transfer object containing the details of the competition to be deleted
     * @return the deleted {@link CompetitionDTO}
     */
    @Operation(
            summary = "Delete a competition",
            description = "This endpoint deletes a competition based on the provided competition data."
    )
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public CompetitionDTO delete(CompetitionDTO competitionDTO) {
        return competitionsService.delete(competitionDTO);
    }

    /**
     * Retrieves all competitions.
     *
     * @return a list of all {@link CompetitionDTO} objects
     */
    @Operation(
            summary = "Get all competitions",
            description = "This endpoint retrieves all competitions available in the system."
    )
    @GET
    public List<CompetitionDTO> getAll() {
        return competitionsService.getAll();
    }

    /**
     * Retrieves competitions by their name.
     *
     * @param name the name of the competition to search for
     * @return a list of {@link CompetitionDTO} objects matching the given name
     */
    @Operation(
            summary = "Get competitions by name",
            description = "This endpoint retrieves competitions that match the specified name."
    )
    @GET
    @Path("/getByName")
    public List<CompetitionDTO> getByName(@QueryParam("name") String name) {
        return competitionsService.getCompetitionsByName(name);
    }

    /**
     * Retrieves competitions by their date.
     *
     * @param date the date of the competition to search for (in String format)
     * @return a list of {@link CompetitionDTO} objects matching the given date
     */
    @Operation(
            summary = "Get competitions by date",
            description = "This endpoint retrieves competitions that occur on the specified date."
    )
    @GET
    @Path("/getByDate")
    public List<CompetitionDTO> getByDate(@QueryParam("date") String date) {
        return competitionsService.getCompetitionsByDate(date);
    }

    /**
     * Retrieves competitions occurring between two dates.
     *
     * @param dateBegin the start date of the range (in String format)
     * @param dateEnd the end date of the range (in String format)
     * @return a list of {@link CompetitionDTO} objects occurring between the specified dates
     */
    @Operation(
            summary = "Get competitions between two dates",
            description = "This endpoint retrieves competitions that occur between two specified dates."
    )
    @GET
    @Path("/getBetweenTwoDates")
    public List<CompetitionDTO> getBetweenTwoDates(@QueryParam("dateBegin") String dateBegin, @QueryParam("dateEnd") String dateEnd) {
        return competitionsService.getCompetitionsBetweenDates(dateBegin, dateEnd);
    }

    /**
     * Updates an existing competition.
     *
     * @param competitionDTO the data transfer object containing the updated competition details
     * @return the updated {@link CompetitionDTO}
     */
    @Operation(
            summary = "Update an existing competition",
            description = "This endpoint updates an existing competition with new details."
    )
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public CompetitionDTO update(EditCompetitionDTO competitionDTO) {
        return competitionsService.update(competitionDTO);
    }
}
