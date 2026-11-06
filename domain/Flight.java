package com.airline.domain;

import java.util.Objects;
public class Flight implements Comparable<Flight> {
    private final String flightNumber;
    private String airline;
    private String destination;
    private double ticketPrice;
    private int availableSeats;
    private double flightRating;


    public Flight(String flightNumber, String airline, String destination,
                  double ticketPrice, int availableSeats, double flightRating) {
        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Номер рейса обязателен");
        }
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.destination = destination;
        this.ticketPrice = ticketPrice;
        this.availableSeats = availableSeats;
        this.flightRating = flightRating;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getAirline() {
        return airline;
    }

    public String getDestination() {
        return destination;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getFlightRating() {
        return flightRating;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public void setAvailableSeats(int availableSeats) {
        this.availableSeats = availableSeats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(flightNumber, flight.flightNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flightNumber);
    }

    @Override
    public int compareTo(Flight other) {
        return this.flightNumber.compareTo(other.flightNumber);
    }

    @Override
    public String toString() {
        return "Flight{Num='" + flightNumber + "', Airline='" + airline + "', Dest='" + destination +
                "', Price=" + ticketPrice + ", Seats=" + availableSeats + ", Rating=" + flightRating + "}";
    }
}
