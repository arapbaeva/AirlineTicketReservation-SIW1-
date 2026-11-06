package com.airline;

import com.airline.comparator.FlightComparators;
import com.airline.domain.BookingRequest;
import com.airline.domain.Flight;
import com.airline.domain.Passenger;
import com.airline.service.AirlineBookingService;
import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
    AirlineBookingService bookingService = new AirlineBookingService();

  System.out.println("=== 1. ИНИЦИАЛИЗАЦИЯ И ДОБАВЛЕНИЕ ДАННЫХ ===");
        Flight f1 = new Flight("SU-100", "Aeroflot", "Moscow", 150.0, 120, 4.8);
        Flight f2 = new Flight("TK-415", "Turkish Airlines", "Istanbul", 280.0, 85, 4.9);
        Flight f3 = new Flight("SU-102", "Aeroflot", "Moscow", 120.0, 45, 4.3);
        Flight f4 = new Flight("EK-201", "Emirates", "Dubai", 550.0, 200, 5.0);

    bookingService.addFlight(f1);
    bookingService.addFlight(f2);
    bookingService.addFlight(f3);
    bookingService.addFlight(f4);

        Passenger p1 = new Passenger("AN-987654", "Иван Иванов", 28, "+996555112233");
        Passenger p2 = new Passenger("AN-123456", "Мария Петрова", 35, "+996700445566");
    bookingService.registerPassenger(p1);
    bookingService.registerPassenger(p2);

    System.out.println("\n=== 2. ПОИСК ЗА O(1) И КАТЕГОРИЗАЦИЯ ===");
        Flight searched = bookingService.getFlightByNumber("TK-415");
    System.out.println("Найден рейс TK-415: "+searched);

        List<Flight> moscowFlights = bookingService.getFlightsByDestination("Moscow");
    System.out.println("Найдено рейсов в 'Moscow': "+moscowFlights.size());

    System.out.println("\n=== 3. СОРТИРОВКА С ИСПОЛЬЗОВАНИЕМ COMPARATOR ===");
    System.out.println("Сортировка по цене билета (возрастание):");
        List<Flight> byPrice = bookingService.getSortedFlights(FlightComparators.BY_PRICE_ASC);
    for(
        Flight f :byPrice)

        {
            System.out.println("   " + f);
        }

    System.out.println("\nСортировка по рейтингу (убывание):");
        List<Flight> byRating = bookingService.getSortedFlights(FlightComparators.BY_RATING_DESC);
    for(
        Flight f :byRating)

        {
            System.out.println("   " + f);
        }

    System.out.println("\n=== 4. ОБРАБОТКА ОЧЕРЕДИ БРОНИРОВАНИЯ (Queue.poll) ===");
        // Приоритеты: 10 = VIP, 5 = Business, 1 = Economy
        BookingRequest req1 = new BookingRequest("REQ-101", p1, f1, 1, LocalDateTime.now());
        BookingRequest req2 = new BookingRequest("REQ-102", p2, f4, 10, LocalDateTime.now()); // VIP
        BookingRequest req3 = new BookingRequest("REQ-103", p1, f2, 5, LocalDateTime.now().plusMinutes(2)); // Business

    bookingService.submitBookingRequest(req1);
    bookingService.submitBookingRequest(req2);
    bookingService.submitBookingRequest(req3);

    System.out.println("Заявок в очереди перед обработкой: "+bookingService.getPendingRequestsCount());

    System.out.println("\nИзвлечение элементов из очереди по приоритету (poll):");
    while(bookingService.getPendingRequestsCount()>0)

        {
            BookingRequest processed = bookingService.processNextBookingRequest();
            System.out.println(" Обработана заявка: " + processed);
        }

    System.out.println("\n=== 5. ИТОГОВАЯ АНАЛИТИКА ===");
    System.out.printf("Средняя цена билета: $%.2f\n",bookingService.calculateAverageTicketPrice());
    System.out.println("Распределение рейсов по городам: "+bookingService.getFlightCountByDestination());
    }
}

