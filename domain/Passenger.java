package com.airline.domain;
import java.util.Objects;
public class Passenger implements Comparable<Passenger> {

    private final String passportNumber;
    private String fullName;
    private int age;
    private String phoneNumber;

    public Passenger(String passportNumber, String fullName, int age, String phoneNumber) {
        if (passportNumber == null || passportNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Номер паспорта не может быть пустым");
        }
        this.passportNumber = passportNumber;
        this.fullName = (fullName != null) ? fullName : "";
        this.age = age;
        this.phoneNumber = phoneNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(passportNumber, passenger.passportNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passportNumber);
    }

    @Override
    public int compareTo(Passenger other) {
        if (other == null || other.fullName == null) return 1;
        if (this.fullName == null) return -1;
        return this.fullName.compareToIgnoreCase(other.fullName);
    }

    @Override
    public String toString() {
        return "Passenger{Passport='" + passportNumber + "', Name='" + fullName + "', Age=" + age + "}";
    }
}
