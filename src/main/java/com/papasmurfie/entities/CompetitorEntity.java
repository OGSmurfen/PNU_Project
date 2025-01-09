package com.papasmurfie.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a competitor entity stored in the database.
 * <p>
 * This class maps to the "Competitors" table in the database and holds the details of a competitor, including
 * the competitor's name, contact information, and associated nationalities. It extends {@link PanacheEntity} to
 * leverage Quarkus' Panache ORM capabilities, providing convenient methods for database operations.
 * </p>
 */
@Entity(name = "Competitors")
public class CompetitorEntity extends PanacheEntity {

    @Column(length = 50, nullable = false)
    private String competitorFirstName;
    @Column(length = 50, nullable = false)
    private String competitorMiddleName;
    @Column(length = 50, nullable = false)
    private String competitorLastName;
    @Column(length = 20, nullable = false, unique = true)
    private String phone;
    @Column(length = 60, nullable = false, unique = true)
    private String email;

    @ManyToMany
    @JoinTable(
            name = "competitor_nationality",
            joinColumns = @JoinColumn(name = "competitor_id"),
            inverseJoinColumns = @JoinColumn(name = "nationality_id")
    )
    private List<NationalityEntity> nationalities = new ArrayList<>();


    /**
     * Gets competitor first name.
     *
     * @return the competitor first name
     */
    public String getCompetitorFirstName() {
        return competitorFirstName;
    }

    /**
     * Sets competitor first name.
     *
     * @param competitorFirstName the competitor first name
     */
    public void setCompetitorFirstName(String competitorFirstName) {
        this.competitorFirstName = competitorFirstName;
    }

    /**
     * Gets competitor middle name.
     *
     * @return the competitor middle name
     */
    public String getCompetitorMiddleName() {
        return competitorMiddleName;
    }

    /**
     * Sets competitor middle name.
     *
     * @param competitorMiddleName the competitor middle name
     */
    public void setCompetitorMiddleName(String competitorMiddleName) {
        this.competitorMiddleName = competitorMiddleName;
    }

    /**
     * Gets competitor last name.
     *
     * @return the competitor last name
     */
    public String getCompetitorLastName() {
        return competitorLastName;
    }

    /**
     * Sets competitor last name.
     *
     * @param competitorLastName the competitor last name
     */
    public void setCompetitorLastName(String competitorLastName) {
        this.competitorLastName = competitorLastName;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets nationalities.
     *
     * @return the nationalities
     */
    public List<NationalityEntity> getNationalities() {
        return nationalities;
    }

    /**
     * Sets nationalities.
     *
     * @param nationality the nationality
     */
    public void setNationalities(List<NationalityEntity> nationality) {
        this.nationalities = nationality;
    }

    @Override
    public String toString() {
        return "CompetitorEntity{" +
                "competitorFirstName='" + competitorFirstName + '\'' +
                ", competitorMiddleName='" + competitorMiddleName + '\'' +
                ", competitorLastName='" + competitorLastName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", nationalities=" + nationalities +
                '}';
    }
}
