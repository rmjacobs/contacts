package com.rmj.contacts.services;

import com.rmj.contacts.model.Address;
import com.rmj.contacts.model.Contact;
import com.rmj.contacts.model.Name;
import com.rmj.contacts.model.Phone;
import com.rmj.contacts.repo.ContactsRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class ContactsServiceTest {
    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {

        @Bean
        public ContactsService contactsService() {
            return new ContactsServiceImpl();
        }
    }

    @Autowired
    private ContactsService service;

    @MockBean
    private ContactsRepository contactsRepository;

    Phone home = new Phone(Phone.PhoneType.home, "1-614-555-1212");
    Phone mobile = new Phone(Phone.PhoneType.mobile, "1-614-555-1213");
    Phone work = new Phone(Phone.PhoneType.work, "1-614-777-1212");

    Name name1 = new Name("John", "Quincy", "Public");
    Name name2 = new Name("Homer", "John", "Simpson");
    Name name3 = new Name("Bart","J","Simpson");
    Name name4 = new Name("Jack", "B", "Torrance");
    String email1 = "john@foo.com";
    String email2 = "homer@springfieldnuclear.com";
    String email3 = "bart@springfieldelementary.com";
    String email4 = "jack@allworknoplay.com";

    Address address1 = new Address("1234 Main Street",
            "Schenectady", "New York", "12345");
    Address address2 = new Address("1234 Main Street",
            "Springfield", "Illinois", "62701");

    Address address3 = new Address("1234 Overlook Way",
            "Timberline", "Oregon", "11111");


    @Test
    public void testGetContacts() {
        Contact contact1 = new Contact(name1, address1, Arrays.asList(home),email1);
        Contact contact2 = new Contact(name2, address2, Arrays.asList(home,work,mobile),email2);

        when(contactsRepository.findAll()).thenReturn(Arrays.asList(contact1, contact2));

        List<Contact> contacts = service.getContacts();

        verify(contactsRepository, times(1)).findAll();
        assert(contacts.equals(Arrays.asList(contact1, contact2)));
    }
    @Test
    public void testGetContactById() {
        Contact contact = new Contact(name1, address1, Arrays.asList(home),email1 );
        contact.setId(1);
        when(contactsRepository.findById(1)).thenReturn(Optional.of(contact));
        Contact retrievedContact = service.getContact(1);

        verify(contactsRepository, times(1)).findById(1);
        assert(retrievedContact.equals(contact));
    }

    @Test
    public void testGetContactByIdNoMatch() {
        Contact contact = new Contact(name1, address1, Arrays.asList(home),email1 );
        contact.setId(1);
        when(contactsRepository.findById(1)).thenReturn(Optional.empty());
        Contact retrievedContact = service.getContact(1);

        verify(contactsRepository, times(1)).findById(1);
        assert(retrievedContact == null);
    }


    @Test
    public void getCallList() {
        Contact contact1 = new Contact(name2, address2, Arrays.asList(home),email2); // Simpson, Homer
        Contact contact2 = new Contact(name1, address1, Arrays.asList(home,work,mobile),email1); // Public, John
        Contact contact3 = new Contact(name3, address2, Arrays.asList(home,work,mobile),email3); // Simpson, Bart
        Contact contact4 = new Contact(name4, address3, Arrays.asList(work), email4); // Torrance, Jack : should not return because there is no home phone for this contact.
        when(contactsRepository.findAllCallList()).thenReturn(Arrays.asList(contact2, contact3,contact1));
        List<Contact> contacts = service.getCallList();

        verify(contactsRepository, times(1)).findAllCallList();
        assert(contacts.get(0).getName().getFirst().equals("John"));
        assert(contacts.get(1).getName().getFirst().equals("Bart"));
        assert(contacts.get(2).getName().getFirst().equals("Homer"));
        assert(!contacts.contains(contact4));

    }

    @Test
    public void storeContact() {
        Contact contact1 = new Contact(name1, address1, Arrays.asList(home),email1);
        when(contactsRepository.save(contact1)).thenReturn(contact1);

        Contact storedContact = service.storeContact(contact1);
        verify(contactsRepository, times(1)).save(contact1);
        assert(storedContact.equals(contact1));

    }

    @Test
    public void deleteContact() {
        Contact contact1 = new Contact(name1, address1, Arrays.asList(home),email1);
        contact1.setId(1);
        when(contactsRepository.findById(1)).thenReturn(Optional.of(contact1));

        boolean wasDeleted = service.deleteContact(1);

        verify(contactsRepository, times(1)).delete(contact1);
        assert(wasDeleted == true);

    }

    @Test
    public void deleteNonExistingContact() {
        Contact contact1 = new Contact(name1, address1, Arrays.asList(home),email1);
        contact1.setId(42);
        when(contactsRepository.findById(1)).thenReturn(Optional.empty());

        boolean wasDeleted = service.deleteContact(42);

        verify(contactsRepository, times(0)).delete(contact1);
        assert(wasDeleted == false);
    }

    @Test
    public void updateContact() {

    }
}
