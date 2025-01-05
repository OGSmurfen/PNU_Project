package com.papasmurfie.dto;

import jakarta.persistence.Column;

public record ContactDTO(String name, String phone, String email) {

    @Override
    public String toString() {
        return String.format("Това е [name=%s, phone=%s, email=%s]", name, phone, email);
    }


}
