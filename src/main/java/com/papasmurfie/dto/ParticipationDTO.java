package com.papasmurfie.dto;

import java.math.BigDecimal;
import java.time.LocalDate;


/**
 * A Data Transfer Object (DTO) representing a participation in a competition.
 * <p>
 * This record holds information about a participation, including the information about the: competitor, competition, event and result.
 * It is immutable and serves as a lightweight data carrier between application layers.
 * </p>
 *
 * @param firstName the name of the competitor
 * @param middleName the middle name of the competitor
 * @param lastName the last name of the competitor
 * @param mobilePhone the mobile phone of the competitor
 * @param competitionName the name of the competition
 * @param competitionDate the date of the competition
 * @param distance the distance ran
 * @param eventType the type of distance - sprint, dash, long etc.
 * @param seconds the time in which the competitor achieved the result
 * @param finished a boolean whether a DNF or a finish took place
 * @param place the placement result of the competitor
 */
public record ParticipationDTO (
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
        String place
){
}
