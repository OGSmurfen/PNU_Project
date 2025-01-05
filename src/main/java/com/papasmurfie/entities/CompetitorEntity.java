package com.papasmurfie.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

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


    public String getCompetitorFirstName() {
        return competitorFirstName;
    }

    public void setCompetitorFirstName(String competitorFirstName) {
        this.competitorFirstName = competitorFirstName;
    }

    public String getCompetitorMiddleName() {
        return competitorMiddleName;
    }

    public void setCompetitorMiddleName(String competitorMiddleName) {
        this.competitorMiddleName = competitorMiddleName;
    }

    public String getCompetitorLastName() {
        return competitorLastName;
    }

    public void setCompetitorLastName(String competitorLastName) {
        this.competitorLastName = competitorLastName;
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

    public List<NationalityEntity> getNationalities() {
        return nationalities;
    }

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
