package com.papasmurfie.dto;

public record ContactEditDTO(Long id, String name, String middleName, String lastName, String phone, String email) {

    @Override
    public String toString() {
        return String.format("Това е [name=%s, phone=%s, email=%s]", name, phone, email);
    }


}
