package com.papasmurfie.dto;

import java.time.LocalDate;

public record EditCompetitionDTO(
        String competitionName,
        LocalDate competitionDate,
        String newCompetitionName,
        LocalDate newCompetitionDate
) {
}
