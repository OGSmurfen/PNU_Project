package com.papasmurfie.dto;

import com.papasmurfie.entities.CompetitionEntity;
import com.papasmurfie.entities.CompetitorEntity;
import com.papasmurfie.entities.EventEntity;
import com.papasmurfie.entities.ResultEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A Data Transfer Object (DTO) representing a request to edit a competitor's participation in a competition.
 * <p>
 * This record holds both the current participation details (competitor information, competition details,
 * event information, and result) and the updated details (new distance, event type, result, etc.) to modify the participation.
 * It is immutable and serves as a lightweight data carrier for passing edit requests between application layers.
 * </p>
 *
 * @param firstName the current first name of the competitor
 * @param middleName the current middle name of the competitor
 * @param lastName the current last name of the competitor
 * @param mobilePhone the current mobile phone number of the competitor
 * @param competitionName the current name of the competition
 * @param competitionDate the current date of the competition
 * @param distance the current distance of the event in the competition
 * @param eventType the current type of the event in the competition
 * @param seconds the current result time (in seconds) of the competitor in the event
 * @param finished indicates whether the competitor has finished the event
 * @param place the current finishing place of the competitor in the event
 * @param newDistance the new distance of the event in the competition (if being updated)
 * @param newEventType the new type of the event in the competition (if being updated)
 * @param newSeconds the new result time (in seconds) of the competitor in the event (if being updated)
 * @param newFinished indicates whether the competitor has finished the event (if being updated)
 * @param newPlace the new finishing place of the competitor in the event (if being updated)
 */
public record EditParticipationDTO(
        String firstName,
        String middleName,
        String lastName,
        String mobilePhone,
        String competitionName,
        LocalDate competitionDate,
        BigDecimal distance,
        String eventType,
        float seconds,
        boolean finished,
        String place,
        BigDecimal newDistance,
        String newEventType,
        float newSeconds,
        boolean newFinished,
        String newPlace
) {
}
