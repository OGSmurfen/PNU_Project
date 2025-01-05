package com.papasmurfie.services;

import com.papasmurfie.dto.EditEventDTO;
import com.papasmurfie.dto.EventDTO;
import com.papasmurfie.entities.EventEntity;
import com.papasmurfie.uow.IUnitOfWork;
import com.papasmurfie.utility.EntityValidator;
import com.papasmurfie.utility.ErrorResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class EventsService {

    private final IUnitOfWork unitOfWork;


    public EventsService(IUnitOfWork unitOfWork) {
        this.unitOfWork = unitOfWork;
    }

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

    @Transactional
    public List<EventDTO> getByEventType(String eventType) {
        return unitOfWork.getEventsRepository().find("eventType", eventType)
                .stream().map(this::maptoDTO).toList();
    }
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
    private EventEntity mapToEntity(EventDTO eventDTO) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setDistance(eventDTO.distance());
        eventEntity.setEventType(eventDTO.eventType());

        return eventEntity;
    }

    private EventDTO maptoDTO(EventEntity eventEntity) {
        return new EventDTO(eventEntity.getDistance(), eventEntity.getEventType());
    }


}
