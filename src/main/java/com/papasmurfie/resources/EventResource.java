package com.papasmurfie.resources;


import com.papasmurfie.dto.EditEventDTO;
import com.papasmurfie.dto.EventDTO;
import com.papasmurfie.services.EventsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.math.BigDecimal;
import java.util.List;

/**
 * RESTful resource class that provides CRUD operations for events.
 * Handles HTTP requests related to event resources.
 * <p>
 * Base path: "/api/v1/event"
 */
@ApplicationScoped
@Path("api/v1/event")
public class EventResource {

    private final EventsService eventsService;

    /**
     * Constructor used for injecting dependencies.
     *
     * @param eventsService the service that handles business logic for events, injected by the DI container
     */
    public EventResource(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    /**
     * Creates a new event.
     *
     * @param eventDTO the event data to be created.
     * @return the created event as a DTO.
     */
    @Operation(
            summary = "Create a new event",
            description = "This endpoint allows you to create a new event by providing the necessary details in the request body."
    )
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public EventDTO create(EventDTO eventDTO) {
        return eventsService.save(eventDTO);
    }

    /**
     * Retrieves all events.
     *
     * @return a list of all event DTOs.
     */
    @Operation(
            summary = "Get all events",
            description = "This endpoint retrieves a list of all events currently in the system."
    )
    @GET
    public List<EventDTO> getAll() {
        return eventsService.getAll();
    }

    /**
     * Retrieves events by their type.
     *
     * @param eventType the type of the event to search for.
     * @return a list of event DTOs matching the specified event type.
     */
    @Operation(
            summary = "Get events by event type",
            description = "This endpoint retrieves events based on the specified event type."
    )
    @GET
    @Path("eventType/{eventType}")
    public List<EventDTO> getByEventType(@PathParam("eventType") String eventType) {
        return eventsService.getByEventType(eventType);
    }

    /**
     * Retrieves events by their distance.
     *
     * @param eventDistance the distance of the event to search for.
     * @return a list of event DTOs matching the specified event distance.
     */
    @Operation(
            summary = "Get events by event distance",
            description = "This endpoint retrieves events based on the specified event distance."
    )
    @GET
    @Path("eventDistance/{eventDistance}")
    public List<EventDTO> getByEventDistance(@PathParam("eventDistance") BigDecimal eventDistance) {
        return eventsService.getByEventDistance(eventDistance);
    }

    /**
     * Deletes an event.
     *
     * @param eventDTO the event data to be deleted.
     * @return the deleted event DTO.
     */
    @Operation(
            summary = "Delete an event",
            description = "This endpoint deletes an event based on the provided event data."
    )
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public EventDTO delete(EventDTO eventDTO) {
        return eventsService.delete(eventDTO);
    }

    /**
     * Updates an existing event.
     *
     * @param editEventDTO the updated event data.
     * @return the updated event DTO.
     */
    @Operation(
            summary = "Update an event",
            description = "This endpoint updates an existing event with the provided updated details."
    )
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public EventDTO update(EditEventDTO editEventDTO) {
        return eventsService.update(editEventDTO);
    }



}
