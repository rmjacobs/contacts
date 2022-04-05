package com.rmj.contacts.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="phone")
public class Phone {
    public Phone(){}
    public Phone(PhoneType type, String number) {
        this.type = type;
        this.number = number;
    }
    public enum PhoneType {
        home,
        work,
        mobile
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonIgnore
    private int id;
    private String number;

    @Enumerated(EnumType.STRING)
    private PhoneType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public PhoneType getType() {
        return type;
    }

    public void setType(PhoneType type) {
        this.type = type;
    }

}
