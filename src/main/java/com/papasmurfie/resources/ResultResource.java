package com.papasmurfie.resources;

import com.papasmurfie.dto.EditResultDTO;
import com.papasmurfie.dto.ResultDTO;
import com.papasmurfie.services.ResultsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@ApplicationScoped
@Path("/result")
public class ResultResource {

    private final ResultsService resultsService;

    public ResultResource(ResultsService resultsService) {
        this.resultsService = resultsService;
    }


    @GET
    public List<ResultDTO> getAll(){
        return resultsService.getAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public ResultDTO post(ResultDTO resultDTO){
        return resultsService.save(resultDTO);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public ResultDTO update(EditResultDTO editResultDTO){
        return resultsService.update(editResultDTO);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public ResultDTO delete(ResultDTO resultDTO){
        return resultsService.delete(resultDTO);
    }

}
