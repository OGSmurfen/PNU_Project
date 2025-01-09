package com.papasmurfie.dto;

import java.util.List;
/**
 * A Data Transfer Object (DTO) representing a competitor.
 * <p>
 * This record holds information about a competitor, including its names, phone, email and nationalities.
 * It is immutable and serves as a lightweight data carrier between application layers.
 * </p>
 *
 * @param firstName the name of the competitor
 * @param middleName the middle name of the competitor
 * @param lastName the last name of the competitor
 * @param mobilePhone the mobile phone of the competitor
 * @param email the email of the competitor
 * @param nationalities list of the nationalities of the competitor
 */
public record CompetitorDTO(String firstName,
                            String middleName,
                            String lastName,
                            String mobilePhone,
                            String email,
                            List<String> nationalities) {
}
