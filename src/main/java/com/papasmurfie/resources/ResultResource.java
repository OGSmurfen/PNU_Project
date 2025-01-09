package com.papasmurfie.resources;

import com.papasmurfie.dto.EditResultDTO;
import com.papasmurfie.dto.ResultDTO;
import com.papasmurfie.services.ResultsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

/**
 * REST resource for managing result-related operations.
 * This class provides CRUD operations for Result entities.
 * <p>
 * Base path: "/nationality"
 */
@ApplicationScoped
@Path("/result")
public class ResultResource {

    private final ResultsService resultsService;

    /**
     * Constructor used for injecting dependencies.
     *
     * @param resultsService the service that handles business logic for handling the results of a competition, injected by the DI container
     */
    public ResultResource(ResultsService resultsService) {
        this.resultsService = resultsService;
    }

    /**
     * Retrieves all results.
     *
     * @return a list of ResultDTO representing all results
     */
    @Operation(
            summary = "Retrieve all results",
            description = "This endpoint retrieves a list of all results available in the system."
    )
    @GET
    public List<ResultDTO> getAll(){
        return resultsService.getAll();
    }

    /**
     * Creates a new result.
     *
     * @param resultDTO the ResultDTO object containing the details of the result
     * @return the created ResultDTO
     */
    @Operation(
            summary = "Create a new result",
            description = "This endpoint is locked. Results are created only using participation endpoint."
    )
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public ResultDTO create(ResultDTO resultDTO){
        return resultsService.save(resultDTO);
    }

    /**
     * Updates an existing result.
     *
     * @param editResultDTO the EditResultDTO object containing the updated details
     * @return the updated ResultDTO
     */
    @Operation(
            summary = "Update an existing result",
            description = "This endpoint updates an existing result with the provided updated details."
    )
    @APIResponse(responseCode = "200", description = "Result updated successfully")
    @APIResponse(responseCode = "404", description = "Result to update not found")
    @APIResponse(responseCode = "500", description = "Unexpected server error")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public ResultDTO update(EditResultDTO editResultDTO){
        return resultsService.update(editResultDTO);
    }

    /**
     * Deletes an existing result.
     *
     * @param resultDTO the ResultDTO object to be deleted
     * @return the deleted ResultDTO
     */
    @Operation(
            summary = "Delete a result",
            description = "This endpoint deletes a result by providing the result details."
    )
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public ResultDTO delete(ResultDTO resultDTO){
        return resultsService.delete(resultDTO);
    }

}
