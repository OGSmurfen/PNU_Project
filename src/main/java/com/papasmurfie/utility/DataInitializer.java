package com.papasmurfie.utility;

import com.papasmurfie.entities.NationalityEntity;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DataInitializer {

    @PostConstruct
    @Transactional
    public void init() {
        System.out.println("Data Initialized");

        NationalityEntity nationalityBulgaria = new NationalityEntity();
        nationalityBulgaria.setCountryName("Bulgaria");

        NationalityEntity nationalityGermany = new NationalityEntity();
        nationalityGermany.setCountryName("Germany");

        nationalityBulgaria.persist();
        nationalityGermany.persist();




    }
}
