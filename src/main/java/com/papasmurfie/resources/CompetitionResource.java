package com.papasmurfie.resources;

import com.papasmurfie.dto.CompetitionDTO;
import com.papasmurfie.services.CompetitionsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/competition")
public class CompetitionResource {

    private final CompetitionsService competitionsService;

    public CompetitionResource(CompetitionsService competitionsService) {
        this.competitionsService = competitionsService;
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public CompetitionDTO create(CompetitionDTO competitionDTO) {
        return competitionsService.save(competitionDTO);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public CompetitionDTO delete(CompetitionDTO competitionDTO) {
        return competitionsService.delete(competitionDTO);
    }

}
