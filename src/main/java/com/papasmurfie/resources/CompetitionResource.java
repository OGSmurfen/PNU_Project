package com.papasmurfie.resources;

import com.papasmurfie.dto.CompetitionDTO;
import com.papasmurfie.dto.EditCompetitionDTO;
import com.papasmurfie.services.CompetitionsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

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

    @GET
    public List<CompetitionDTO> getAll() {
        return competitionsService.getAll();
    }

    @GET
    @Path("/getByName")
    public List<CompetitionDTO> getByName(@QueryParam("name") String name) {
        return competitionsService.getCompetitionsByName(name);
    }

    @GET
    @Path("/getByDate")
    public List<CompetitionDTO> getByDate(@QueryParam("date") String date) {
        return competitionsService.getCompetitionsByDate(date);
    }
    @GET
    @Path("/getBetweenTwoDates")
    public List<CompetitionDTO> getBetweenTwoDates(@QueryParam("dateBegin") String dateBegin, @QueryParam("dateEnd") String dateEnd) {
        return competitionsService.getCompetitionsBetweenDates(dateBegin, dateEnd);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public CompetitionDTO update(EditCompetitionDTO competitionDTO) {
        return competitionsService.update(competitionDTO);
    }
}
