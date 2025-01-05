package com.papasmurfie.resources;

import com.papasmurfie.dto.ContactDTO;
import com.papasmurfie.dto.ContactEditDTO;
import com.papasmurfie.services.ContactsService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@ApplicationScoped
@Path("api/v1/contact")
public class ContactResource {

    private final ContactsService contactsService;

    public ContactResource(ContactsService contactsService) {
        this.contactsService = contactsService;
    }

    @GET
    public List<ContactDTO> list(){
        return contactsService.GetAllContacts();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public ContactDTO create(ContactDTO contactDTO){
        return contactsService.save(contactDTO);
    }

    @DELETE
    @Path("/{id}")
    public ContactDTO delete(@PathParam("id")Long id){
        return contactsService.delete(id);
    }


    @PUT
    public ContactDTO update(ContactEditDTO contactEditDTO){
        return contactsService.update(contactEditDTO);
    }
}
