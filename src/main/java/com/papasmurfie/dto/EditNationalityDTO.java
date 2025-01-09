package com.papasmurfie.dto;

/**
 * A Data Transfer Object (DTO) representing a request to edit a nationality.
 * <p>
 * This record holds both the current nationality and the new nationality name to modify the nationality.
 * It is immutable and serves as a lightweight data carrier for passing edit requests between application layers.
 * </p>
 *
 * @param currentNationalityName the current name of the nationality
 * @param newNationalityName the new name of the nationality (if being updated)
 */
public record EditNationalityDTO(
        String currentNationalityName,
        String newNationalityName
) {
}
