package com.travel.service;

import com.travel.model.Person;

import java.util.List;

public class UserService {

    // Metodo per eseguire il login
    public Person login(String email, String password, List<Person> users) {
        for (Person user : users) {
            if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null; // Restituisce null se le credenziali sono errate
    }
}