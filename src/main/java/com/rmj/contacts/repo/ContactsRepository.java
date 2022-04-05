package com.rmj.contacts.repo;

import com.rmj.contacts.model.Contact;
import com.rmj.contacts.model.Phone;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public interface ContactsRepository extends JpaRepository<Contact, Integer> {
    public default List<Contact> findAllCallList() {
        Sort sortBy = Sort.by("name.last").and(Sort.by("name.first"));
        List<Contact> callList = findAll(sortBy);
        callList = callList.stream().filter(c ->
                c.getPhone().stream().anyMatch(a ->
                        a.getType().equals(Phone.PhoneType.home))
        ).collect(Collectors.toList());
        return callList;
    }
}
