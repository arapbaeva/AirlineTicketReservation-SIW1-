package com.airline.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class BookingRequest implements Comparable<BookingRequest> {
    private final String requestId;
    private final Passenger passenger;
    private final Flight flight;
    private final int priorityScore;
    private final LocalDateTime requestTime;
    private String status;
    // 10 = VIP, 5 = Business, 1 = Economy private final LocalDateTime requestTime; private String status;

    public BookingRequest(String requestId, Passenger passenger, Flight flight,
                          int priorityScore, LocalDateTime requestTime) {
        if (requestId == null || requestId.trim().isEmpty()) {
            throw new IllegalArgumentException("ID заявки не может быть пустым");
        }
        if (passenger == null || flight == null) {
            throw new IllegalArgumentException("Пассажир и рейс не могут быть null");
        }
        this.requestId = requestId;
        this.passenger = passenger;
        this.flight = flight;
        this.priorityScore = priorityScore;
        this.requestTime = (requestTime != null) ? requestTime : LocalDateTime.now();
        this.status = "NEW";
    }

    public String getRequestId() {
        return requestId;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public Flight getFlight() {
        return flight;
    }

    public int getPriorityScore() {
        return priorityScore;
    }

    public LocalDateTime getRequestTime() {
        return requestTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookingRequest that = (BookingRequest) o;
        return Objects.equals(requestId, that.requestId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }

    @Override
    public int compareTo(BookingRequest other) {
        if (other == null) return 1;

        // Сначала сравниваем по приоритету (от большего к меньшему)
        if (this.priorityScore != other.priorityScore) {
            return Integer.compare(other.priorityScore, this.priorityScore);
        }

        // При равном приоритете сравниваем по времени создания
        if (this.requestTime == null && other.requestTime == null) return 0;
        if (this.requestTime == null) return -1;
        if (other.requestTime == null) return 1;
        return this.requestTime.compareTo(other.requestTime);
    }

    @Override
    public String toString() {
        return "BookingReq{ID='" + requestId + "', Passenger='" + passenger.getFullName() +
                "', Flight='" + flight.getFlightNumber() + "', Priority=" + priorityScore +
                ", Status='" + status + "'}";
    }
}
