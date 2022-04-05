package com.rmj.contacts.services;

import com.rmj.contacts.model.Contact;
import com.rmj.contacts.repo.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ContactsServiceImpl implements ContactsService {
    @Autowired
    private final ContactsRepository contactsRepository;

    public ContactsServiceImpl(ContactsRepository contactsRepository) {

        this.contactsRepository = contactsRepository;
    }

    @Override
    public List<Contact> getContacts() {

        return contactsRepository.findAll();
    }

    @Override
    public List<Contact> getCallList() {
        return contactsRepository.findAllCallList();
    }

    @Override
    public Contact getContact(int id) {
        Optional<Contact> contact = contactsRepository.findById(id);
        return contact.orElse(null);
    }

    @Override
    public Contact storeContact(Contact contact) {
        return contactsRepository.save(contact);
    }

    @Override
    public boolean deleteContact(int id) {
        boolean deleted = false;
        Optional<Contact> contact = contactsRepository.findById(id);

        if (contact.isPresent()) {
            contactsRepository.delete(contact.get());
            deleted = true;
        }
        return deleted;
    }
}
