package com.papasmurfie.dto;

import java.util.List;

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