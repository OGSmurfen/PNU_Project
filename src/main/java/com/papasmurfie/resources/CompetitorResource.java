package com.papasmurfie.resources;

import com.papasmurfie.dto.CompetitorDTO;
import com.papasmurfie.dto.EditCompetitorDTO;
import com.papasmurfie.services.CompetitorsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import javax.print.attribute.standard.Media;
import java.util.List;

@ApplicationScoped
@Path("api/v1/competitor")
public class CompetitorResource {

    private final CompetitorsService competitorsService;

    public CompetitorResource(CompetitorsService competitorsService) {
        this.competitorsService = competitorsService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public CompetitorDTO save(CompetitorDTO competitorDTO) {
        return competitorsService.save(competitorDTO);
    }
    @GET
    public List<CompetitorDTO> list(){
        return competitorsService.getAll();
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public CompetitorDTO delete(CompetitorDTO competitorDTO) {
        return competitorsService.delete(competitorDTO);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public CompetitorDTO update(EditCompetitorDTO competitorDTO) {
        return competitorsService.update(competitorDTO);
    }
}
