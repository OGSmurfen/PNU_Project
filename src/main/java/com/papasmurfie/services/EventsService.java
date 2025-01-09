package com.papasmurfie.services;

import com.papasmurfie.dto.EditEventDTO;
import com.papasmurfie.dto.EventDTO;
import com.papasmurfie.entities.EventEntity;
import com.papasmurfie.uow.IUnitOfWork;
import com.papasmurfie.utility.EntityValidator;
import com.papasmurfie.utility.ErrorResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service class responsible for handling the business logic of events, including saving, retrieving, updating,
 * and deleting event data from the repository.
 * <p>
 * This service class is annotated as {@link ApplicationScoped}, making it a CDI (Contexts and Dependency Injection)
 * bean available for dependency injection throughout the application.
 */
@ApplicationScoped
public class EventsService {

    private final IUnitOfWork unitOfWork;

    /**
     * Constructs a {@link EventsService} with the provided unit of work.
     *
     * @param unitOfWork The unit of work used to interact with the repositories.
     */
    public EventsService(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

    /**
     * Saves a new event to the repository.
     * <p>
     * Validates that the event with the given distance does not already exist before saving the event.
     *
     * @param eventDTO The {@link EventDTO} containing the event data to save.
     * @return The {@link EventDTO} of the saved event.
     * @throws WebApplicationException If an event with the same distance already exists.
     */
    @Transactional
    public EventDTO save(EventDTO eventDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("distance", eventDTO.distance());
        //map.put("eventType", eventDTO.eventType());

        EntityValidator.validateUnique(unitOfWork.getEventsRepository(),
                map,
                "Event of distance '"+ eventDTO.distance() +"' already exists");

        EventEntity eventEntity = mapToEntity(eventDTO);
        unitOfWork.getEventsRepository().persist(eventEntity);

        return eventDTO;
    }

    /**
     * Retrieves all events from the repository.
     * <p>
     * Returns a list of {@link EventDTO}. If no events are found, a {@link WebApplicationException} is thrown.
     *
     * @return A list of {@link EventDTO} representing all events.
     * @throws WebApplicationException If no events are found in the repository.
     */
    @Transactional
    public List<EventDTO> getAll(){
        List<EventDTO> eventEntities = unitOfWork.getEventsRepository().findAll().stream().map(this::maptoDTO).toList();
        if(eventEntities.isEmpty()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.CONFLICT)
                            .entity(new ErrorResponse(
                                    404,
                                    "Not found",
                                    "No events found"
                            ))
                            .type("application/json")
                            .build()
            );
        }
        return eventEntities;
    }

    /**
     * Retrieves events by their type.
     *
     * @param eventType The type of events to search for.
     * @return A list of {@link EventDTO} matching the given event type.
     */
    @Transactional
    public List<EventDTO> getByEventType(String eventType) {
        return unitOfWork.getEventsRepository().find("eventType", eventType)
                .stream().map(this::maptoDTO).toList();
    }

    /**
     * Retrieves events by their distance.
     * <p>
     * If no events with the given distance are found, a {@link WebApplicationException} is thrown.
     *
     * @param distance The distance of events to search for.
     * @return A list of {@link EventDTO} matching the given distance.
     * @throws WebApplicationException If no events are found with the given distance.
     */
    @Transactional
    public List<EventDTO> getByEventDistance(BigDecimal distance) {
        List<EventDTO> events = unitOfWork.getEventsRepository().find("distance", distance)
                .stream()
                .map(this::maptoDTO)
                .toList();

        if (events.isEmpty()) {
            throw new WebApplicationException(
                    Response.status(Response.Status.NOT_FOUND)
                            .entity(new ErrorResponse(
                                    404,
                                    "Not found",
                                    "No events found"
                            ))
                            .type("application/json")
                            .build()
            );
        }


        return events;
    }

    /**
     * Deletes an event based on the provided {@link EventDTO}.
     * <p>
     * Validates the existence of the event before deleting it.
     *
     * @param eventDTO The {@link EventDTO} representing the event to delete.
     * @return The {@link EventDTO} of the deleted event.
     * @throws WebApplicationException If the event to delete does not exist.
     */
    @Transactional
    public EventDTO delete(EventDTO eventDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("distance", eventDTO.distance());
        map.put("eventType", eventDTO.eventType());

        EventEntity eventEntity = (EventEntity) EntityValidator.validateExists(
                unitOfWork.getEventsRepository(),
                map,
                "Such entity does not exist."
        );

        unitOfWork.getEventsRepository().delete(eventEntity);
        return eventDTO;
    }

    /**
     * Updates an existing event with new data from the provided {@link EditEventDTO}.
     * <p>
     * Validates that the event exists before updating its details.
     *
     * @param editEventDTO The {@link EditEventDTO} containing the updated event data.
     * @return The updated {@link EventDTO}.
     * @throws WebApplicationException If the event does not exist.
     */
    @Transactional
    public EventDTO update(EditEventDTO editEventDTO) {
        Map<String, Object> map = new HashMap<>();
        map.put("distance", editEventDTO.distance());
        map.put("eventType", editEventDTO.eventType());
        EventEntity eventEntity = (EventEntity) EntityValidator.validateExists(
                unitOfWork.getEventsRepository(),
                map,
                "Such entity does not exist."
        );

        eventEntity.setDistance(editEventDTO.newDistance());
        eventEntity.setEventType(editEventDTO.newEventType());

        return maptoDTO(eventEntity);
    }



    // Mappers
    /**
     * Converts an {@link EventDTO} to an {@link EventEntity}.
     *
     * @param eventDTO The {@link EventDTO} to convert.
     * @return The corresponding {@link EventEntity}.
     */
    private EventEntity mapToEntity(EventDTO eventDTO) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setDistance(eventDTO.distance());
        eventEntity.setEventType(eventDTO.eventType());

        return eventEntity;
    }

    /**
     * Converts an {@link EventEntity} to an {@link EventDTO}.
     *
     * @param eventEntity The {@link EventEntity} to convert.
     * @return The corresponding {@link EventDTO}.
     */
    private EventDTO maptoDTO(EventEntity eventEntity) {
        return new EventDTO(eventEntity.getDistance(), eventEntity.getEventType());
    }


}
