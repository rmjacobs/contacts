package com.rmj.contacts.controller;

import com.rmj.contacts.model.Contact;
import com.rmj.contacts.services.ContactsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("contacts")
@RestController
public class ContactsController {

    private final ContactsService contactsService;

    @Autowired
    public ContactsController(ContactsService contactsServiceService) {
        this.contactsService = contactsServiceService;
    }

    @GetMapping()
    public List<Contact> getContacts() {
        return contactsService.getContacts();
    }

    @GetMapping("/call-list")
    public List<Contact> getCallLst() {
        return contactsService.getCallList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contact> getContact(@PathVariable("id") int id) {
        Contact contact = contactsService.getContact(id);
        return contact != null ? new ResponseEntity<>(contact, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<String> storeContact(@RequestBody Contact contact) {
        return contactsService.storeContact(contact) != null ? new ResponseEntity<>("Saved Successfully", HttpStatus.OK)
                : new ResponseEntity<>("Not Saved Successfully", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> storeContact(@PathVariable("id") int id, @RequestBody Contact contact) {
        Contact exContact = contactsService.getContact(id);
        contact.setId(id);
        return exContact != null ? contactsService.storeContact(contact) != null
                ? new ResponseEntity<>("Updated Successfully", HttpStatus.OK)
                : new ResponseEntity<>("Not Updated Successfully", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable("id") int id) {
        Contact exContact = contactsService.getContact(id);
        return exContact != null ? contactsService.deleteContact(id)
                ? new ResponseEntity<>("Deleted Successfully", HttpStatus.OK)
                : new ResponseEntity<>("Not Deleted Successfully", HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
