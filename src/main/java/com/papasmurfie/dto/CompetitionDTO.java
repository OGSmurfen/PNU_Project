package com.papasmurfie.dto;

import java.time.LocalDate;

public record CompetitionDTO(
        String competitionName,
        LocalDate competitionDate
) {
}
