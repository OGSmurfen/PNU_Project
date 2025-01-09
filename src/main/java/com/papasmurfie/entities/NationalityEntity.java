package com.papasmurfie.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Represents a nationality entity stored in the database.
 * <p>
 * This class maps to the "Nationalities" table in the database and holds the details of a nationality, including
 * the country's name and the list of competitors associated with that nationality. It extends {@link PanacheEntity}
 * to leverage Quarkus' Panache ORM capabilities, which simplify database interactions.
 * </p>
 */
@Entity(name = "Nationalities")
public class NationalityEntity extends PanacheEntity {

    @Column(length = 50, unique = true)
    private String countryName;

    @ManyToMany(mappedBy = "nationalities")
    private List<CompetitorEntity> competitors = new ArrayList<>();

    /**
     * Gets country name.
     *
     * @return the country name
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets country name.
     *
     * @param countryName the country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }



}
