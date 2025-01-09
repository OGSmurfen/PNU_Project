package com.papasmurfie.resources;

import com.papasmurfie.dto.EditParticipationDTO;
import com.papasmurfie.dto.ParticipationDTO;
import com.papasmurfie.services.ParticipationsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.math.BigDecimal;
import java.util.List;

/**
 * REST resource for managing participation-related operations.
 * This class provides CRUD operations for Participation entities
 * and allows querying participations based on various criteria.
 * <p>
 * Base path: "/nationality"
 */
@ApplicationScoped
@Path("/participation")
public class ParticipationResource {

    private final ParticipationsService participationsService;


    /**
     * Constructor used for injecting dependencies.
     *
     * @param participationsService the service that handles business logic for participations, injected by the DI container
     */
    public ParticipationResource(ParticipationsService participationsService) {
        this.participationsService = participationsService;
    }


    /**
     * Retrieves all participations.
     *
     * @return a list of ParticipationDTO representing all participations
     */
    @Operation(
            summary = "Retrieve all participations",
            description = "This endpoint retrieves a list of all participations available in the system."
    )
    @GET
    public List<ParticipationDTO> getAll(){
        return participationsService.findAll();
    }

    /**
     * Retrieves participations by the competitor's name.
     *
     * @param firstName the first name of the competitor (optional)
     * @param middleName the middle name of the competitor (optional)
     * @param lastName the last name of the competitor (optional)
     * @return a list of ParticipationDTO representing participations of the given competitor(s)
     */
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getByNames")
    @Operation(
            summary = "Retrieve participations by competitor's name",
            description = "This endpoint retrieves participations based on the competitor's full name."
    )
    public List<ParticipationDTO> getByNames(@QueryParam("firstName")String firstName,
                                             @QueryParam("middleName")String middleName,
                                             @QueryParam("lastName")String lastName){
        return participationsService.findByNames(firstName, middleName, lastName);
    }

    /**
     * Retrieves participations by competition details.
     *
     * @param competitionName the name of the competition (optional)
     * @param competitionDate the date of the competition (optional)
     * @return a list of ParticipationDTO representing participations in the specified competition
     */
    @GET
    @Path("/getByCompetition")
    @Operation(
            summary = "Retrieve participations by competition",
            description = "This endpoint retrieves participations in a specific competition based on competition name and date."
    )
    public List<ParticipationDTO> getByCompetition(@QueryParam("competitionName")String competitionName,
                                             @QueryParam("competitionDate")String competitionDate){
        return participationsService.findByCompetition(competitionName, competitionDate);
    }

    /**
     * Retrieves participations by event distance.
     *
     * @param distance the event distance to filter participations by
     * @return a list of ParticipationDTO representing participations in events with the specified distance
     */
    @GET
    @Path("/getByDistance")
    @Operation(
            summary = "Retrieve participations by event distance",
            description = "This endpoint retrieves participations that match a given event distance."
    )
    public List<ParticipationDTO> getByDistance(@QueryParam("eventDistance")BigDecimal distance){
        return participationsService.findByDistance(distance);
    }

    /**
     * Retrieves participations by time finished.
     *
     * @param timeFinished the time finished to filter participations by
     * @return a list of ParticipationDTO representing participations with the specified time
     */
    @GET
    @Path("/getByTime")
    @Operation(
            summary = "Retrieve participations by time finished",
            description = "This endpoint retrieves participations that match a specific time finished."
    )
    public List<ParticipationDTO> getByTime(@QueryParam("timeFinished")float timeFinished){
        return participationsService.findByTime(timeFinished);
    }

    /**
     * Retrieves participations by placement (rank).
     *
     * @param place the placement (rank) to filter participations by
     * @return a list of ParticipationDTO representing participations with the specified placement
     */
    @Operation(
            summary = "Retrieve participations by placement",
            description = "This endpoint retrieves participations filtered by placement/rank."
    )
    @GET
    @Path("/getByPlace")
    public List<ParticipationDTO> getByPlace(@QueryParam("placement")String place){
        return participationsService.findByPlacement(place);
    }

    /**
     * Creates a new participation.
     *
     * @param participationDTO the ParticipationDTO object containing the details of the participation
     * @return the created ParticipationDTO
     */
    @Operation(
            summary = "Create a new participation",
            description = "This endpoint creates a new participation by providing the necessary details."
    )
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public ParticipationDTO create(ParticipationDTO participationDTO){
        return participationsService.save(participationDTO);
    }

    /**
     * Updates an existing participation.
     *
     * @param editParticipationDTO the EditParticipationDTO object containing the updated details
     * @return the updated ParticipationDTO
     */
    @Operation(
            summary = "Update an existing participation",
            description = "This endpoint updates an existing participation with the provided updated details."
    )
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public ParticipationDTO update(EditParticipationDTO editParticipationDTO){
        return participationsService.update(editParticipationDTO);
    }

    /**
     * Deletes a participation.
     *
     * @param participationDTO the ParticipationDTO object to be deleted
     * @return the deleted ParticipationDTO
     */
    @Operation(
            summary = "Delete a participation",
            description = "This endpoint deletes a participation by providing the participation details."
    )
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public ParticipationDTO delete(ParticipationDTO participationDTO){
        return participationsService.delete(participationDTO);
    }

}
