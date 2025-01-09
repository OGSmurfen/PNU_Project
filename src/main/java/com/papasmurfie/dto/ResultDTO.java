package com.papasmurfie.dto;

/**
 * A Data Transfer Object (DTO) representing the result of a competition.
 * <p>
 * This record holds information about the result - time, placement and whether it was finished or a DNF.
 * It is immutable and serves as a lightweight data carrier between application layers.
 * </p>
 *
 * @param seconds the time in which the competitor achieved the result
 * @param finished a boolean whether a DNF or a finish took place
 * @param place the placement result of the competitor
 */
public record ResultDTO (
        float seconds,
        boolean finished,
        String place
){
}
