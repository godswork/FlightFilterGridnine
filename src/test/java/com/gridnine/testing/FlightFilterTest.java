package com.gridnine.testing;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FlightFilterTest {

    private List<Flight> createFlights() {
        LocalDateTime now = LocalDateTime.now();
        return List.of(
                // Вылет в будущем
                new Flight(List.of(new Segment(now.plusDays(1), now.plusDays(1).plusHours(2)))),

                // Вылет в прошлом
                new Flight(List.of(new Segment(now.minusDays(1), now.plusDays(1).plusHours(2)))),

                // Вылет до прибытия
                new Flight(List.of(new Segment(now.plusDays(2), now.minusDays(2).plusHours(2)))),

                // Время на земле > 2 часов
                new Flight(List.of(
                        new Segment(now.plusDays(1), now.plusDays(1).plusHours(2)),
                        new Segment(now.plusDays(6).plusHours(4), now.plusDays(6).plusHours(5))
                ))
        );
    }

    @Test
    void testDepartureBeforeNowFilter() {
        List<Flight> flights = createFlights();
        FlightFilter flightFilter = new FlightFilterImpl();
        List<Flight> filteredFlights = flightFilter.departureBeforeNowFilter(flights);

        // Ожидаем 3, так как один из них в прошлом
        assertEquals(3, filteredFlights.size());
    }

    @Test
    void testArrivalBeforeDepartureFilter() {
        List<Flight> flights = createFlights();
        FlightFilter flightFilter = new FlightFilterImpl();
        List<Flight> filteredFlights = flightFilter.arrivalBeforeDepartureFilter(flights);

        // Ожидаем 3, так как один из них вылетает раньше, чем приземляется
        assertEquals(3, filteredFlights.size());
    }

    @Test
    void testGroundTimeExceedsFilter() {
        List<Flight> flights = createFlights();
        FlightFilter flightFilter = new FlightFilterImpl();
        List<Flight> filteredFlights = flightFilter.groundTimeExceedsFilter(flights);

        // Ожидаем 3, так как один из них имеет время на земле > 2ч
        assertEquals(3, filteredFlights.size());
    }
}
