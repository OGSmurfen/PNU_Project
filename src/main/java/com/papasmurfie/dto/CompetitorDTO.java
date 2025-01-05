package com.papasmurfie.dto;

import java.util.List;

public record CompetitorDTO(String firstName,
                            String middleName,
                            String lastName,
                            String mobilePhone,
                            String email,
                            List<String> nationalities) {
}
