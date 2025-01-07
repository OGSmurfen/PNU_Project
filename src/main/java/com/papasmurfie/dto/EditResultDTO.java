package com.papasmurfie.dto;

public record EditResultDTO (
        float seconds,
        boolean finished,
        String place,
        float newSeconds,
        boolean newFinished,
        String newPlace
){
}
