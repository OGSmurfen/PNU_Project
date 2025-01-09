package com.papasmurfie.dto;

/**
 * A Data Transfer Object (DTO) representing a nationality.
 * <p>
 * This record holds information about a nationality - the country name.
 * It is immutable and serves as a lightweight data carrier between application layers.
 * </p>
 *
 * @param countryName the name of the country the nationality refers to
 */
public record NationalityDTO(String countryName) {
}
