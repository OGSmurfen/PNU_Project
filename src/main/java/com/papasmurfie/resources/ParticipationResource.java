package com.papasmurfie.resources;

import com.papasmurfie.dto.EditParticipationDTO;
import com.papasmurfie.dto.ParticipationDTO;
import com.papasmurfie.entities.CompetitionEntity;
import com.papasmurfie.entities.CompetitorEntity;
import com.papasmurfie.services.ParticipationsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@ApplicationScoped
@Path("/participation")
public class ParticipationResource {

    private final ParticipationsService participationsService;


    public ParticipationResource(ParticipationsService participationsService) {
        this.participationsService = participationsService;
    }


    @GET
    public List<ParticipationDTO> getAll(){
        return participationsService.findAll();
    }

    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getByNames")
    public List<ParticipationDTO> getByNames(@QueryParam("firstName")String firstName,
                                             @QueryParam("middleName")String middleName,
                                             @QueryParam("lastName")String lastName){
        return participationsService.findByNames(firstName, middleName, lastName);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public ParticipationDTO create(ParticipationDTO participationDTO){
        return participationsService.save(participationDTO);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public ParticipationDTO update(EditParticipationDTO editParticipationDTO){
        return participationsService.update(editParticipationDTO);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public ParticipationDTO delete(ParticipationDTO participationDTO){
        return participationsService.delete(participationDTO);
    }

}
