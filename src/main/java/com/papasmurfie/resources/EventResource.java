package com.papasmurfie.resources;


import com.papasmurfie.dto.EditEventDTO;
import com.papasmurfie.dto.EventDTO;
import com.papasmurfie.services.EventsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
@Path("api/v1/event")
public class EventResource {

    private final EventsService eventsService;

    public EventResource(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public EventDTO create(EventDTO eventDTO) {
        return eventsService.save(eventDTO);
    }

    @GET
    public List<EventDTO> getAll() {
        return eventsService.getAll();
    }

    @GET
    @Path("eventType/{eventType}")
    public List<EventDTO> getByEventId(@PathParam("eventType") String eventType) {
        return eventsService.getByEventType(eventType);
    }

    @GET
    @Path("eventDistance/{eventDistance}")
    public List<EventDTO> getByEventDistance(@PathParam("eventDistance") BigDecimal eventDistance) {
        return eventsService.getByEventDistance(eventDistance);
    }

    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public EventDTO delete(EventDTO eventDTO) {
        return eventsService.delete(eventDTO);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public EventDTO update(EditEventDTO editEventDTO) {
        return eventsService.update(editEventDTO);
    }



}
