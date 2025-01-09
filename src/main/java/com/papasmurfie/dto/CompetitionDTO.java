package com.papasmurfie.dto;

import java.time.LocalDate;
/**
 * A Data Transfer Object (DTO) representing a competition.
 * <p>
 * This record holds information about a competition, including its name and date.
 * It is immutable and serves as a lightweight data carrier between application layers.
 * </p>
 *
 * @param competitionName the name of the competition
 * @param competitionDate the date of the competition
 */
public record CompetitionDTO(
        String competitionName,
        LocalDate competitionDate
) {
}
