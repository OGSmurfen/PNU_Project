package com.papasmurfie.dto;

/**
 * A Data Transfer Object (DTO) representing a request to edit a competitor's result in an event.
 * <p>
 * This record holds both the current result details (time in seconds, finished status, and place)
 * and the updated result details (new time, new finished status, and new place) to modify the result.
 * It is immutable and serves as a lightweight data carrier for passing edit requests between application layers.
 * </p>
 *
 * @param seconds the current result time (in seconds) of the competitor in the event
 * @param finished indicates whether the competitor has finished the event
 * @param place the current finishing place of the competitor in the event
 * @param newSeconds the new result time (in seconds) of the competitor in the event (if being updated)
 * @param newFinished indicates whether the competitor has finished the event (if being updated)
 * @param newPlace the new finishing place of the competitor in the event (if being updated)
 */
public record EditResultDTO (
        float seconds,
        boolean finished,
        String place,
        float newSeconds,
        boolean newFinished,
        String newPlace
){
}
