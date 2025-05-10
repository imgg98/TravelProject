package com.travel.app;

import com.travel.model.JacksonUtils;
import com.travel.model.Person;
import com.travel.model.Role;
import com.travel.model.Trip;
import com.travel.service.TripService;
import com.travel.service.UserService;

import static com.travel.config.AppConfig.USERS_FILE;
import static com.travel.config.AppConfig.TRIPS_FILE;

import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Crea una nuova istanza di UserService
        UserService userService = new UserService();

        // Variabili locali
        List<Person> users = JacksonUtils.loadUsers(USERS_FILE);
        initializeUsersIfEmpty(users, USERS_FILE);

        List<Trip> trips = JacksonUtils.loadTrips(TRIPS_FILE);
        TripService tripService = new TripService(trips);

        System.out.println("Welcome to TravelProject!");

        // Procede con il login
        Person currentUser = login(users, scanner);
        if (currentUser == null) {
            System.out.println("❌ Login failed. Exiting...");
            return;
        }

        boolean running = true;
        while (running) {
            if (currentUser.getRole() == Role.ORGANIZER) {
                System.out.println("\n📋 Organizer Menu");
                System.out.println("1. ➕ Create a new trip");
                System.out.println("2. ✏️ Modify an existing trip");
                System.out.println("3. 📅 Show trips and roles");
                System.out.println("4. 👥 Add participants or organizers");
                System.out.println("5. 🗑️ Remove users");
                System.out.println("6. 📄 View trips");
                System.out.println("7. 💾 Save and exit");
                System.out.print("Choose an option: ");
            } else if (currentUser.getRole() == Role.PARTICIPANT) {
                System.out.println("\n📋 Participant Menu");
                System.out.println("1. 📄 View trips");
                System.out.println("2. 💾 Save and exit");
                System.out.print("Choose an option: ");
            }

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    Trip currentTrip = tripService.createTrip(currentUser, scanner);
                    trips.add(currentTrip);
                    System.out.println("✅ Trip \"" + currentTrip.getName() + "\" created successfully.");
                    break;
                case "2":
                    System.out.print("Enter the name of the trip you want to modify: ");
                    String tripNameToModify = scanner.nextLine();
                    Trip tripToModify = tripService.findTripByName(tripNameToModify);
                    if (tripToModify != null) {
                        tripService.modifyTrip(tripToModify, scanner);
                        System.out.println("✅ Trip modified successfully!");
                    } else {
                        System.out.println("❌ Trip not found.");
                    }
                    break;
                case "3":
                    tripService.displayTrips();
                    break;
                case "4":
                    System.out.print("Enter the name of the trip to add participants or organizers: ");
                    String tripNameToAddParticipants = scanner.nextLine();
                    Trip tripToAddParticipants = tripService.findTripByName(tripNameToAddParticipants);
                    if (tripToAddParticipants != null) {
                        tripService.addParticipantsOrOrganizers(tripToAddParticipants, scanner, users, trips,
                                USERS_FILE, TRIPS_FILE);
                    } else {
                        System.out.println("❌ Trip not found.");
                    }
                    break;
                case "5":
                    System.out.print("Enter the name of the trip to remove users: ");
                    String tripNameToRemoveUsers = scanner.nextLine();
                    Trip tripToRemoveUsers = tripService.findTripByName(tripNameToRemoveUsers);
                    if (tripToRemoveUsers != null) {
                        tripService.removeUserFromTrip(tripToRemoveUsers, scanner);
                    } else {
                        System.out.println("❌ Trip not found.");
                    }
                    break;
                case "6":
                    tripService.displayTrips();
                    break;
                case "7":
                    JacksonUtils.saveUsers(users, "users.json");
                    JacksonUtils.saveTrips(trips, "trips.json");
                    System.out.println("💾 Data saved. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid option.");
            }
        }
    }

    // Metodo per inizializzare gli utenti se il file è vuoto
    public static void initializeUsersIfEmpty(List<Person> users, String filename) {
        if (users.isEmpty()) {
            System.out.println("No users found. Creating a default user...");
            Person defaultUser = new Person("Giulio", "Giangrande", "YDCCTRF654E",
                    "giulio.giangrande@example.com", "333 1234567" , LocalDate.parse("1990-01-01"),
                    "password123", Role.ORGANIZER);
            users.add(defaultUser);
            JacksonUtils.saveUsers(users, "users.json");
            System.out.println("Default user created.");
        }
    }


    // Metodo per eseguire il login tramite UserService
    public static Person login(List<Person> users, Scanner scanner) {
        int attempts = 0;
        int maxAttempts = 3;

        while (attempts < maxAttempts) {
            System.out.println("🔐 Login");
            System.out.print("Email: ");
            String email = scanner.nextLine().trim();
            System.out.print("Password: ");
            String password = scanner.nextLine().trim();

            if (email.isEmpty() || password.isEmpty()) {
                System.out.println("❌ Email or password cannot be empty.");
                attempts++;
                continue;
            }

            for (Person user : users) {
                if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
                    System.out.println("✅ Login successful.");
                    return user;
                }
            }

            System.out.println("❌ Invalid credentials.");
            attempts++;

            if (attempts >= maxAttempts) {
                System.out.println("🚫 Too many failed attempts. Try again later.");
                return null;
            }
        }

        return null;
    }
}