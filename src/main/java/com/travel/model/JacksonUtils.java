package com.travel.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JacksonUtils {

    // Logger per la gestione delle eccezioni
    private static final Logger LOGGER = Logger.getLogger(JacksonUtils.class.getName());

    // Adattatore per LocalDate
    public static class LocalDateSerializer extends com.fasterxml.jackson.databind.JsonSerializer<LocalDate> {
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public void serialize(LocalDate value, com.fasterxml.jackson.core.JsonGenerator gen,
                              com.fasterxml.jackson.databind.SerializerProvider serializers)
                throws IOException {
            gen.writeString(value.format(formatter)); // Serializza come Stringa
        }
    }

    // Classe annidata che serve a deserializzare una data (LocalDate) da JSON
    public static class LocalDateDeserializer extends com.fasterxml.jackson.databind.JsonDeserializer<LocalDate> {
        // Specifica il formato atteso della data: "anno-mese-giorno"
        private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        @Override
        public LocalDate deserialize(com.fasterxml.jackson.core.JsonParser p,
                                     com.fasterxml.jackson.databind.DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            // Converte la stringa letta dal file JSON in un oggetto LocalDate usando il formatter
            return LocalDate.parse(p.getText(), formatter);
        }
    }

    // Configurazione comune di ObjectMapper
    public static ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(LocalDate.class, new LocalDateSerializer());
        module.addDeserializer(LocalDate.class, new LocalDateDeserializer());
        mapper.registerModule(module);
        return mapper;
    }

    // Metodo per caricare gli utenti da un file JSON
    public static List<Person> loadUsers(String filePath) {
        ObjectMapper mapper = createObjectMapper();
        File file = new File(filePath);

        try {
            // Se il file non esiste o è vuoto, restituisci una lista vuota
            if (!file.exists() || file.length() == 0) {
                return new ArrayList<>();
            }

            // Usa TypeReference per deserializzare direttamente in una lista
            return mapper.readValue(file, new TypeReference<List<Person>>() {});
        } catch (IOException e) {
            System.err.println("⚠️ Error loading users from " + filePath + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Metodo per salvare gli utenti su un file JSON
    public static void saveUsers(List<Person> users, String filePath) {
        ObjectMapper mapper = createObjectMapper();
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                file.createNewFile();  // Crea il file se non esiste
            }

            // Salva i dati nel file JSON con una formattazione leggibile
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, users);
            System.out.println("Users saved to " + filePath);
        } catch (IOException e) {
            System.err.println("⚠️ Error saving users to " + filePath + ": " + e.getMessage());
        }
    }

    // Metodo per caricare i viaggi da un file JSON
    public static List<Trip> loadTrips(String filePath) {
        ObjectMapper mapper = createObjectMapper(); // Usa il mapper corretto
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                file.createNewFile();
                return new ArrayList<>();
            }

            if (file.length() == 0) {
                return new ArrayList<>();
            }

            Trip[] tripsArray = mapper.readValue(file, Trip[].class);
            return new ArrayList<>(Arrays.asList(tripsArray));
        } catch (IOException e) {
            System.err.println("⚠️ Error loading trips from " + filePath + ": " + e.getMessage());
            return new ArrayList<>();
        }
    }

    // Metodo per salvare i viaggi su un file JSON
    public static void saveTrips(List<Trip> trips, String tripsFile) {
        ObjectMapper mapper = createObjectMapper();

        // Debug: stampa i viaggi prima di salvarli
        System.out.println("Trips to save:");
        for (Trip t : trips) {
            System.out.println(t);  // Stampa ogni viaggio nella lista
        }

        try {
            // Usa direttamente un File per scrivere
            File file = new File(tripsFile);
            if (!file.exists()) {
                file.createNewFile();  // Crea il file se non esiste
            }

            // Scrive i dati nel file JSON
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, trips);
            LOGGER.info("Trips saved to " + tripsFile);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving trips to file", e);
        }
    }
}