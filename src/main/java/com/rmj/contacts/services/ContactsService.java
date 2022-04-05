package com.rmj.contacts.services;


import com.rmj.contacts.model.Contact;

import java.util.List;

public interface ContactsService {

    List<Contact> getContacts();

    List<Contact> getCallList();

    Contact getContact(int id);

    Contact storeContact(Contact contact);

    boolean deleteContact(int id);
}