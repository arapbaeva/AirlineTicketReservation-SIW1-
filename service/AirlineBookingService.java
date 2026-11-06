package com.airline.service;

import com.airline.domain.BookingRequest; import com.airline.domain.Flight; import com.airline.domain.Passenger;
import java.util.*;
public class AirlineBookingService { private final Map<String, Flight> flightPrimaryIndex = new HashMap<>(); private final Map<String, Passenger> passengerPrimaryIndex = new HashMap<>(); private final Map<String, List<Flight>> flightsByDestinationIndex = new HashMap<>(); private final Map<String, Set<BookingRequest>> passengerBookingsIndex = new HashMap<>(); private final PriorityQueue<BookingRequest> bookingExecutionQueue = new PriorityQueue<>();


// Добавление рейса в систему
    public void addFlight(Flight flight) {
        if (flight == null) {
            throw new IllegalArgumentException("Объект рейса не может быть null");
        }
        if (flightPrimaryIndex.containsKey(flight.getFlightNumber())) {
            throw new IllegalArgumentException("Рейс с номером " + flight.getFlightNumber() + " уже существует.");
        }

        // 1. Добавляем в основной Map (поиск за O(1))
        flightPrimaryIndex.put(flight.getFlightNumber(), flight);

        // 2. Добавляем в индекс по городам назначения
        String destKey = flight.getDestination().toLowerCase();
        if (!flightsByDestinationIndex.containsKey(destKey)) {
            flightsByDestinationIndex.put(destKey, new ArrayList<>());
        }
        flightsByDestinationIndex.get(destKey).add(flight);
    }

    // Быстрый поиск за O(1)
    public Flight getFlightByNumber(String flightNumber) {
        return flightPrimaryIndex.get(flightNumber);
    }

    // Удаление рейса из всех коллекций
    public boolean removeFlight(String flightNumber) {
        Flight flight = flightPrimaryIndex.remove(flightNumber);
        if (flight != null) {
            // Удаляем из списка по назначению
            String destKey = flight.getDestination().toLowerCase();
            List<Flight> destList = flightsByDestinationIndex.get(destKey);
            if (destList != null) {
                destList.remove(flight);
                if (destList.isEmpty()) {
                    flightsByDestinationIndex.remove(destKey);
                }
            }

            // Удаляем заявки на этот рейс из очереди через Iterator
            Iterator<BookingRequest> iterator = bookingExecutionQueue.iterator();
            while (iterator.hasNext()) {
                BookingRequest req = iterator.next();
                if (req.getFlight().equals(flight)) {
                    iterator.remove();
                }
            }
            return true;
        }
        return false;
    }

    // Регистрация пассажира
    public void registerPassenger(Passenger passenger) {
        if (passenger == null) {
            throw new IllegalArgumentException("Пассажир не может быть null");
        }
        if (passengerPrimaryIndex.containsKey(passenger.getPassportNumber())) {
            throw new IllegalArgumentException("Пассажир с таким паспортом уже зарегистрирован.");
        }
        passengerPrimaryIndex.put(passenger.getPassportNumber(), passenger);
        passengerBookingsIndex.put(passenger.getPassportNumber(), new HashSet<>());
    }

    public Passenger getPassengerByPassport(String passportNumber) {
        return passengerPrimaryIndex.get(passportNumber);
    }

    // Подача заявки на бронирование в очередь
    public void submitBookingRequest(BookingRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("Заявка не может быть null");
        }
        bookingExecutionQueue.offer(request);

        Set<BookingRequest> userBookings = passengerBookingsIndex.get(request.getPassenger().getPassportNumber());
        if (userBookings != null) {
            userBookings.add(request);
        }
    }

    // Извлечение и обработка первой заявки из очереди (poll)
    public BookingRequest processNextBookingRequest() {
        BookingRequest request = bookingExecutionQueue.poll();
        if (request != null) {
            request.setStatus("CONFIRMED");
            Flight flight = request.getFlight();
            if (flight.getAvailableSeats() > 0) {
                flight.setAvailableSeats(flight.getAvailableSeats() - 1);
            }
        }
        return request;
    }

    // Поиск списка рейсов по назначению
    public List<Flight> getFlightsByDestination(String destination) {if (destination == null) return Collections.emptyList();
        List<Flight> result = flightsByDestinationIndex.get(destination.toLowerCase());
        if (result == null) {
            return Collections.emptyList();
        }
        return result;
    }

    // Сортировка с использованием выбранного Comparator
    public List<Flight> getSortedFlights(Comparator<Flight> comparator) {
        List<Flight> flights = new ArrayList<>(flightPrimaryIndex.values());
        Collections.sort(flights, comparator);
        return flights;
    }

    // Аналитика: расчет средней цены билетов через простой цикл
    public double calculateAverageTicketPrice() {
        if (flightPrimaryIndex.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Flight flight : flightPrimaryIndex.values()) {
            sum += flight.getTicketPrice();
        }
        return sum / flightPrimaryIndex.size();
    }

    // Аналитика: подсчет количества рейсов по направлениям
    public Map<String, Integer> getFlightCountByDestination() {
        Map<String, Integer> countMap = new HashMap<>();
        for (Flight flight : flightPrimaryIndex.values()) {
            String dest = flight.getDestination();
            if (countMap.containsKey(dest)) {
                countMap.put(dest, countMap.get(dest) + 1);
            } else {
                countMap.put(dest, 1);
            }
        }
        return countMap;
    }

    public int getPendingRequestsCount() {
        return bookingExecutionQueue.size();
    }
}
