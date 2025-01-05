package com.papasmurfie.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "Nationalities")
public class NationalityEntity extends PanacheEntity {

    @Column(length = 50, unique = true)
    private String countryName;

    @ManyToMany(mappedBy = "nationalities")
    private List<CompetitorEntity> competitors = new ArrayList<>();

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }



}
