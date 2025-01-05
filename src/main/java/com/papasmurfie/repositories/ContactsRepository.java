package com.papasmurfie.repositories;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import com.papasmurfie.entities.ContactEntity;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;


@ApplicationScoped
public class ContactsRepository implements PanacheRepository<ContactEntity> {

}
