package com.airline.comparator;
import com.airline.domain.Flight; import java.util.Comparator;


public class FlightComparators {
    // 1. Направление (А-Я), затем Цена (по возрастанию)
    public static final Comparator<Flight> BY_DESTINATION_AND_PRICE = new Comparator<Flight>() {
        @Override
        public int compare(Flight f1, Flight f2) {
            int destCompare = f1.getDestination().compareToIgnoreCase(f2.getDestination());
            if (destCompare != 0) {
                return destCompare;
            }
            return Double.compare(f1.getTicketPrice(), f2.getTicketPrice());
        }
    };

    // 2. Цена билета (по возрастанию)
    public static final Comparator<Flight> BY_PRICE_ASC = new Comparator<Flight>() {
        @Override
        public int compare(Flight f1, Flight f2) {
            return Double.compare(f1.getTicketPrice(), f2.getTicketPrice());
        }
    };

    // 3. Рейтинг рейса (по убыванию)
    public static final Comparator<Flight> BY_RATING_DESC = new Comparator<Flight>() {
        @Override
        public int compare(Flight f1, Flight f2) {
            return Double.compare(f2.getFlightRating(), f1.getFlightRating());
        }
    };
}
