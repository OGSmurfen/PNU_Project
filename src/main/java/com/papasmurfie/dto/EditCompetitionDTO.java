package com.papasmurfie.dto;

import java.time.LocalDate;

/**
 * A Data Transfer Object (DTO) representing a request to edit a competition.
 * <p>
 * This record contains both the current details of the competition and the updated details
 * to modify the competition's name and/or date. It is immutable and serves as a lightweight
 * data carrier for passing edit requests between application layers.
 * </p>
 *
 * @param competitionName the current name of the competition
 * @param competitionDate the current date of the competition
 * @param newCompetitionName the new name of the competition (if being updated)
 * @param newCompetitionDate the new date of the competition (if being updated)
 */
public record EditCompetitionDTO(
        String competitionName,
        LocalDate competitionDate,
        String newCompetitionName,
        LocalDate newCompetitionDate
) {
}
