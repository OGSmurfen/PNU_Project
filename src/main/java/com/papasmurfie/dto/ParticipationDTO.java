package com.papasmurfie.dto;

import com.papasmurfie.entities.CompetitionEntity;
import com.papasmurfie.entities.CompetitorEntity;
import com.papasmurfie.entities.EventEntity;
import com.papasmurfie.entities.ResultEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

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
