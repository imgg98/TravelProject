package com.travel.service;

import com.travel.model.JacksonUtils;
import com.travel.model.Person;
import com.travel.model.Role;
import com.travel.model.Trip;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import static com.travel.config.AppConfig.USERS_FILE;
import static com.travel.config.AppConfig.TRIPS_FILE;

public class TripService {
    private List<Trip> trips;

    // Costruttore che accetta la lista di viaggi
    public TripService(List<Trip> trips) {
        this.trips = trips;
    }

    // Metodo per creare un viaggio
    public Trip createTrip(Person currentUser, Scanner scanner) {
        System.out.print("Enter trip name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter destination: ");
        String destination = scanner.nextLine().trim();

        System.out.print("Enter start date (YYYY-MM-DD): ");
        String startDate = scanner.nextLine().trim();

        System.out.print("Enter end date (YYYY-MM-DD): ");
        String endDate = scanner.nextLine().trim();

        Trip trip = new Trip(name, destination, startDate.toString(), endDate.toString());

        // Aggiungi il currentUser come organizer
        trip.getOrganizers().add(currentUser);

        return trip;
    }

    // Metodo per trovare un viaggio per nome
    public Trip findTripByName(String name) {
        for (Trip trip : trips) {
            if (trip.getName().equalsIgnoreCase(name)) {
                return trip;
            }
        }
        return null;
    }

    // Metodo per modificare un viaggio
    public void modifyTrip(Trip trip, Scanner scanner) {
        System.out.print("Enter new destination: ");
        trip.setDestination(scanner.nextLine().trim());

        System.out.print("Enter new start date (YYYY-MM-DD): ");
        LocalDate newStartDate = readValidDate(scanner);
        trip.setStartDate(newStartDate.toString());

        System.out.print("Enter new end date (YYYY-MM-DD): ");
        LocalDate newEndDate = readValidDate(scanner);
        trip.setEndDate(newEndDate.toString());

        System.out.println("✅ Trip updated successfully.");
    }

    // Metodo per visualizzare tutti i viaggi
    public void displayTrips() {
        if (trips.isEmpty()) {
            System.out.println("❌ No trips available.");
            return;
        }
        for (Trip trip : trips) {
            System.out.println("Trip: " + trip.getName() + ", Destination: " + trip.getDestination());
        }
    }

    // Metodo per aggiungere partecipanti/organizzatori
    public void addParticipantsOrOrganizers(Trip trip, Scanner scanner, List<Person> users, List<Trip> trips,
                                            String usersFile, String tripsFile) {
        System.out.print("Do you want to add a participant or an organizer? (p/o): ");
        String type = scanner.nextLine().trim().toLowerCase();

        if (!type.equals("p") && !type.equals("o")) {
            System.out.println("❌ Invalid choice. Use 'p' for participant or 'o' for organizer.");
            return;
        }

        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine().trim();

        System.out.print("Enter tax code: ");
        String taxCode = scanner.nextLine().trim();

        System.out.print("Enter email: ");
        String email = scanner.nextLine().trim();

        System.out.print("Enter phone number: ");
        String phoneNumber = scanner.nextLine().trim();

        System.out.print("Enter birth date (YYYY-MM-DD): ");
        LocalDate birthDate = LocalDate.parse(scanner.nextLine().trim());

        System.out.print("Enter password: ");
        String password = scanner.nextLine().trim();

        // Imposta automaticamente il ruolo
        Role role = type.equals("p") ? Role.PARTICIPANT : Role.ORGANIZER;

        Person newPerson = new Person(firstName, lastName, taxCode, email, phoneNumber, birthDate, password, role);
        users.add(newPerson); // Aggiunge alla lista globale degli utenti

        if (role == Role.PARTICIPANT) {
            trip.getParticipants().add(newPerson);
            System.out.println("✅ Participant added successfully.");
        } else {
            trip.getOrganizers().add(newPerson);
            System.out.println("✅ Organizer added successfully.");
        }

        JacksonUtils.saveUsers(users, USERS_FILE);
        JacksonUtils.saveTrips(trips, TRIPS_FILE);

    }

    // Metodo per rimuovere utenti dal viaggio
    public void removeUserFromTrip(Trip trip, Scanner scanner) {
        System.out.print("Enter first name of the user to remove: ");
        String firstName = scanner.nextLine().trim();

        System.out.print("Enter last name of the user to remove: ");
        String lastName = scanner.nextLine().trim();

        Person toRemove = null;

        // Cerca nei partecipanti
        for (Person participant : trip.getParticipants()) {
            if (participant.getFirstName().equalsIgnoreCase(firstName) &&
                    participant.getLastName().equalsIgnoreCase(lastName)) {
                toRemove = participant;
                trip.getParticipants().remove(toRemove);
                System.out.println("✅ User removed from participants.");
                return;
            }
        }

        // Cerca negli organizzatori
        for (Person organizer : trip.getOrganizers()) {
            if (organizer.getFirstName().equalsIgnoreCase(firstName) &&
                    organizer.getLastName().equalsIgnoreCase(lastName)) {
                toRemove = organizer;
                trip.getOrganizers().remove(toRemove);
                System.out.println("✅ User removed from organizers.");
                return;
            }
        }

        System.out.println("❌ User not found in participants or organizers.");
    }

    private LocalDate readValidDate(Scanner scanner) {
        while (true) {
            try {
                return LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.print("❌ Invalid date format, please use YYYY-MM-DD: ");
            }
        }
    }
}