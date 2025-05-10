package com.travel.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Person implements Serializable {
    private String firstName;
    private String lastName;
    private String taxCode;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private String password;
    private Role role; // "ORGANIZER" o "PARTICIPANT"

    public Person(String firstName, String lastName, String taxCode, String email, String phoneNumber,
                  LocalDate birthDate, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.taxCode = taxCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.password = password;
        this.role = role;
    }

    public Person(String firstName, String lastName, String taxCode, String email, String phoneNumber,
                  LocalDate birthDate, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.taxCode = taxCode;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.role = role;
    }

    public Person(String firstName, String lastName, String email, String password, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person() {
    }

    // Getters e Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}