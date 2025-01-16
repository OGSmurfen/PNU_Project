package com.papasmurfie.rest;


import com.papasmurfie.dto.EditEventDTO;
import com.papasmurfie.dto.EventDTO;
import com.papasmurfie.resources.EventResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;


/**
 * Test class for the {@link com.papasmurfie.resources.EventResource} REST resource.
 * This class contains test cases to validate the functionality of the EventResource endpoints.
 */
@QuarkusTest
public class EventResourceTest {

    private final EventResource eventResource;

    /**
     * Constructor for the test class.
     *
     * @param eventResource the {@link EventResource} to inject the resource being tested
     */
    public EventResourceTest(EventResource eventResource) {
        this.eventResource = eventResource;
    }


    /**
     * Tests the creation of an event from the resource.
     * Verifies the event is successfully created and performs cleanup by deleting the created event.
     */
    @Transactional
    @Test
    public void testCreateEvent(){
        EventDTO dto = new EventDTO(new BigDecimal(123), "Test Event");
        EventDTO createdDto = eventResource.create(dto);

        assert(dto.eventType().equals(createdDto.eventType()) && dto.distance().equals(createdDto.distance()));

        eventResource.delete(createdDto);
    }


    /**
     * Tests the retrieval of all events from the resource.
     * Verifies that the returned list is not empty.
     */
    @Transactional
    @Test
    public void testGetAll() {
        List<EventDTO> dtos = eventResource.getAll();

        assert !dtos.isEmpty();
    }

    /**
     * Tests the retrieval of all events from the resource by their type.
     * Verifies that the returned list is not empty.
     */
    @Transactional
    @Test
    public void testGetByType(){
        EventDTO dto = new EventDTO(new BigDecimal(123), "Test Event");
        EventDTO createdDto = eventResource.create(dto);

        List<EventDTO> response = eventResource.getByEventType(dto.eventType()).stream().toList();

        assert !response.isEmpty();

        eventResource.delete(createdDto);
    }


    /**
     * Tests the retrieval of all events from the resource by their distance.
     * Verifies that the returned list is not empty.
     */
    @Transactional
    @Test
    public void testGetByDistance(){
        EventDTO dto = new EventDTO(new BigDecimal(123), "Test Event");
        EventDTO createdDto = eventResource.create(dto);

        List<EventDTO> response = eventResource.getByEventDistance(new BigDecimal(123));

        assert !response.isEmpty();

        eventResource.delete(createdDto);
    }


    /**
     * Tests the deletion of an event via the resource.
     * Verifies the event is successfully deleted.
     */
    @Transactional
    @Test
    public void testDelete(){
        EventDTO dto = new EventDTO(new BigDecimal(123), "Test Event");
        EventDTO createdDto = eventResource.create(dto);
        EventDTO deletedDto = eventResource.delete(createdDto);

        assert(deletedDto.eventType().equals(dto.eventType()) && deletedDto.distance().equals(dto.distance()));

    }

    /**
     * Tests the update of an existing event via the resource.
     * Verifies the event is updated correctly and performs cleanup by deleting the updated event.
     */
    @Transactional
    @Test
    public void testUpdate(){
        EventDTO dto = new EventDTO(new BigDecimal(123), "Test Event");
        EventDTO createdDto = eventResource.create(dto);
        EditEventDTO editEventDTO = new EditEventDTO(
                dto.distance(),
                dto.eventType(),
                new BigDecimal(456),
                "Updated Test Event"
        );

        EventDTO updated = eventResource.update(editEventDTO);

        assert(updated.distance().compareTo(new BigDecimal(456)) == 0 && updated.eventType().equals("Updated Test Event"));

        eventResource.delete(updated);
    }


}
