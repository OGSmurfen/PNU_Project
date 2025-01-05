package com.papasmurfie.services;

import com.papasmurfie.dto.ContactDTO;
import com.papasmurfie.dto.ContactEditDTO;
import com.papasmurfie.entities.ContactEntity;
import com.papasmurfie.repositories.ContactsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ContactsService {

    private final ContactsRepository contactsRepository;

    public ContactsService(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    public List<ContactDTO> GetAllContacts() {
        return contactsRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private ContactDTO mapToDto(ContactEntity c) {
        return new ContactDTO(
                String.format("%s %s %s",  c.getfirstName(),
                c.getMiddleName(),
                c.getLastName()),
                c.getEmail(),
                c.getPhone()


        );
    }

    @Transactional
    public ContactDTO delete(Long id) {
        ContactEntity entity = contactsRepository.findById(id);
        if(contactsRepository.isPersistent(entity)) {
            contactsRepository.delete(entity);
        }
        return mapToDto(entity);
    }

    private ContactEntity mapToEntity(ContactDTO c) {
        ContactEntity entity = new ContactEntity();
        String[] name = c.name().split(" ");
        entity.setfirstName(name[0]);
        entity.setMiddleName(name[1]);
        entity.setLastName(name[2]);
        entity.setPhone(c.phone());
        entity.setEmail(c.email());
        return entity;
    }

    @Transactional
    public ContactDTO save(ContactDTO contactDTO) {
        ContactEntity entity = mapToEntity(contactDTO);
        contactsRepository.persist(entity);
        return contactDTO;
    }

    @Transactional
    public ContactDTO update(ContactEditDTO contactEditDTO) {
        ContactEntity entity = contactsRepository.findById(contactEditDTO.id());
        if(contactsRepository.isPersistent(entity)) {
            entity.setfirstName(contactEditDTO.name());
            entity.setMiddleName(contactEditDTO.middleName());
            entity.setLastName(contactEditDTO.lastName());
            entity.setPhone(contactEditDTO.phone());
            entity.setEmail(contactEditDTO.email());
            contactsRepository.persist(entity);
        }
        return mapToDto(entity);
    }


}
