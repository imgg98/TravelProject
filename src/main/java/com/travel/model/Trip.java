package com.travel.model;

import java.util.ArrayList;
import java.util.List;

public class Trip {
    private String name;
    private String destination;
    private String startDate;
    private String endDate;
    private List<Person> organizers = new ArrayList<>();
    private List<Person> participants = new ArrayList<>();

    // Costruttore vuoto per deserializzazione JSON
    public Trip() {
    }

    public Trip(String name, String destination, String startDate, String endDate) {
        this.name = name;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.organizers = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Person> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(List<Person> organizers) {
        this.organizers = organizers;
    }

    public List<Person> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Person> participants) {
        this.participants = participants;
    }

    @Override
    public String toString() {
        return "Trip{" +
                "name='" + name + '\'' +
                ", destination='" + destination + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", organizers=" + organizers +
                ", participants=" + participants +
                '}';
    }
}