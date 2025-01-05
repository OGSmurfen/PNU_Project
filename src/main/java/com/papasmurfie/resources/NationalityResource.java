package com.papasmurfie.resources;


import com.papasmurfie.dto.EditNationalityDTO;
import com.papasmurfie.dto.NationalityDTO;
import com.papasmurfie.services.NationalitiesService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import java.util.List;

@ApplicationScoped
@Path("api/v1/nationality")
public class NationalityResource {

    private final NationalitiesService nationalitiesService;

    public NationalityResource(NationalitiesService nationalitiesService) {
        this.nationalitiesService = nationalitiesService;
    }




    @GET
    public List<NationalityDTO> list(){
        return nationalitiesService.getAll();
    }
    @GET
    @Path("/{countryPartialName}")
    public List<NationalityDTO> get(@PathParam("countryPartialName") String countryPartialName){
        return nationalitiesService.getNationalitiesByPartialName(countryPartialName);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public NationalityDTO create(NationalityDTO nationality){
        return nationalitiesService.save(nationality);
    }

    @DELETE
    @Path("/{countryName}")
    public NationalityDTO delete(@PathParam("countryName") String countryName){
        return nationalitiesService.delete(countryName);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(responseCode = "200", description = "Nationality updated successfully")
    @APIResponse(responseCode = "404", description = "Nationality to edit not found")
    @APIResponse(responseCode = "500", description = "Unexpected server error")
    public NationalityDTO update(EditNationalityDTO dto){
        return nationalitiesService.update(dto);
    }
}
