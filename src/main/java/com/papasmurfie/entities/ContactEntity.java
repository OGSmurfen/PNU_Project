package com.papasmurfie.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

/***
 * 
 */


@Entity(name = "Contacts")
public class ContactEntity extends PanacheEntity {
    @Column(name = "FirstName", length = 20, nullable = false)
    private String firstName;
    @Column(name = "MiddleName", length = 60)
    private String middlefirstName;
    @Column(name = "LastName", length = 60, nullable = false)
    private String lastfirstName;
    @Column(name = "Phone", length = 20, nullable = false, unique = true)
    private String phone;
    @Column(name = "Email", length = 60, nullable = false, unique = true)
    private String email;

    public String getfirstName() {
        return firstName;
    }

    public void setfirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middlefirstName;
    }

    public void setMiddleName(String middleName) {
        this.middlefirstName = middleName;
    }

    public String getLastName() {
        return lastfirstName;
    }

    public void setLastName(String lastName) {
        this.lastfirstName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
