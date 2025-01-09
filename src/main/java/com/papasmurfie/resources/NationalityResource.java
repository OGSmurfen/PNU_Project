package com.papasmurfie.resources;


import com.papasmurfie.dto.EditNationalityDTO;
import com.papasmurfie.dto.NationalityDTO;
import com.papasmurfie.services.NationalitiesService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

/**
 * RESTful resource class that provides CRUD operations for nationalities.
 * Handles HTTP requests related to nationality resources.
 * <p>
 * Base path: "/api/v1/nationality"
 */
@ApplicationScoped
@Path("api/v1/nationality")
public class NationalityResource {

    private final NationalitiesService nationalitiesService;

    /**
     * Constructor used for injecting dependencies.
     *
     * @param nationalitiesService the service that handles business logic for nationalities, injected by the DI container
     */
    public NationalityResource(NationalitiesService nationalitiesService) {
        this.nationalitiesService = nationalitiesService;
    }


    /**
     * Retrieves all nationalities.
     *
     * @return a list of all nationality DTOs.
     */
    @Operation(
            summary = "Retrieve all nationalities",
            description = "This endpoint retrieves a list of all nationalities available in the system."
    )
    @GET
    public List<NationalityDTO> list(){
        return nationalitiesService.getAll();
    }

    /**
     * Retrieves nationalities by a partial country name.
     *
     * @param countryPartialName the partial country name to search for.
     * @return a list of nationality DTOs that match the partial country name.
     */
    @Operation(
            summary = "Retrieve nationalities by partial country name",
            description = "This endpoint retrieves nationalities matching a partial country name."
    )
    @GET
    @Path("/{countryPartialName}")
    public List<NationalityDTO> get(@PathParam("countryPartialName") String countryPartialName){
        return nationalitiesService.getNationalitiesByPartialName(countryPartialName);
    }

    /**
     * Creates a new nationality.
     *
     * @param nationality the nationality data to be created.
     * @return the created nationality as a DTO.
     */
    @Operation(
            summary = "Create a new nationality",
            description = "This endpoint allows you to create a new nationality by providing the necessary details."
    )
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public NationalityDTO create(NationalityDTO nationality){
        return nationalitiesService.save(nationality);
    }

    /**
     * Deletes a nationality.
     *
     * @param countryName the name of the country to be deleted.
     * @return the deleted nationality as a DTO.
     */
    @Operation(
            summary = "Delete a nationality",
            description = "This endpoint deletes a nationality based on the country name."
    )
    @DELETE
    @Path("/{countryName}")
    public NationalityDTO delete(@PathParam("countryName") String countryName){
        return nationalitiesService.delete(countryName);
    }

    /**
     * Updates an existing nationality.
     *
     * @param dto the updated nationality data.
     * @return the updated nationality DTO.
     */
    @Operation(
            summary = "Update an existing nationality",
            description = "This endpoint updates an existing nationality with the provided updated details."
    )
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Nationality updated successfully")
    @APIResponse(responseCode = "404", description = "Nationality to edit not found")
    @APIResponse(responseCode = "500", description = "Unexpected server error")
    public NationalityDTO update(EditNationalityDTO dto){
        return nationalitiesService.update(dto);
    }
}
