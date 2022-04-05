package com.rmj.contacts.repo;

import com.rmj.contacts.model.Address;
import com.rmj.contacts.model.Contact;
import com.rmj.contacts.model.Name;
import com.rmj.contacts.model.Phone;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ContactsRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ContactsRepository repository;

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

        // Populate  Repository with more than one entity
        entityManager.persist(contact1);
        entityManager.persist(contact2);
        List<Contact> contacts = repository.findAll();

        assert(contacts.size() == 2);
        assert(contacts.get(0).getId()  == 1);
        assert(contacts.get(0).getAddress().equals(address1));
        assert(contacts.get(0).getPhone().get(0).equals(home));
        assert(contacts.get(0).getName().equals(name1));
        assert(contacts.get(0).getEmail().equals(email1));
        assert(contacts.get(1).getId()  == 2);
        assert(contacts.get(1).getAddress().equals(address2));
        assert(contacts.get(1).getPhone().get(0).equals(home));
        assert(contacts.get(1).getPhone().get(1).equals(work));
        assert(contacts.get(1).getPhone().get(2).equals(mobile));
        assert(contacts.get(1).getName().equals(name2));
        assert(contacts.get(1).getEmail().equals(email2));
    }

    @Test
    public void testGetContactById() {
        Contact contact = new Contact(name1, address1, Arrays.asList(home),email1 );

        entityManager.persist(contact);
        Contact retrievedContact = repository.findById(1).get();

        assert(retrievedContact.getId()  == 1);
        assert(retrievedContact.getAddress().equals(address1));
        assert(retrievedContact.getPhone().get(0).equals(home));
        assert(retrievedContact.getName().equals(name1));
        assert(retrievedContact.getEmail().equals(email1));
    }


    @Test
    public void getCallList() {
        Contact contact1 = new Contact(name2, address2, Arrays.asList(home),email2);
        Contact contact2 = new Contact(name1, address1, Arrays.asList(home,work,mobile),email1);
        Contact contact3 = new Contact(name3, address2, Arrays.asList(home,work,mobile),email3);
        Contact contact4 = new Contact(name4, address3, Arrays.asList(work), email4);

        entityManager.persist(contact1); // Simpson, Homer
        entityManager.persist(contact2); // Public, John
        entityManager.persist(contact3); // Simpson, Bart
        entityManager.persist(contact4); // Torrance, Jack : should not return because there is no home phone for this contcat.

        List<Contact> contacts = repository.findAllCallList();

        assert(contacts.get(0).getName().getFirst().equals("John"));
        assert(contacts.get(1).getName().getFirst().equals("Bart"));
        assert(contacts.get(2).getName().getFirst().equals("Homer"));
        assert(!contacts.contains(contact4));

    }

}

