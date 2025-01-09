package com.papasmurfie.dto;

import java.util.List;

/**
 * A Data Transfer Object (DTO) representing a request to edit a competitor's details.
 * <p>
 * This record holds both the current details of a competitor and the updated details
 * to modify the competitor's name, contact information, and nationality. It is immutable
 * and serves as a lightweight data carrier for passing edit requests between application layers.
 * </p>
 *
 * @param firstName the current first name of the competitor
 * @param middleName the current middle name of the competitor
 * @param lastName the current last name of the competitor
 * @param mobilePhone the current mobile phone number of the competitor
 * @param email the current email address of the competitor
 * @param nationalities the current list of nationalities of the competitor
 * @param newFirstName the new first name of the competitor (if being updated)
 * @param newMiddleName the new middle name of the competitor (if being updated)
 * @param newLastName the new last name of the competitor (if being updated)
 * @param newMobilePhone the new mobile phone number of the competitor (if being updated)
 * @param newEmail the new email address of the competitor (if being updated)
 * @param newNationalities the new list of nationalities of the competitor (if being updated)
 */
public record EditCompetitorDTO (      String firstName,
                                       String middleName,
                                       String lastName,
                                       String mobilePhone,
                                       String email,
                                       List<String> nationalities,
                                       String newFirstName,
                                       String newMiddleName,
                                       String newLastName,
                                       String newMobilePhone,
                                       String newEmail,
                                       List<String> newNationalities)
{

}