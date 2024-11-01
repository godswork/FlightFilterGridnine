package com.gridnine.testing;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("\nПерелёты с вылетом после текущего времени:");
        List<Flight> departureAfterNowFlights = new FlightFilterImpl().groundTimeExceedsFilter(flights);
        departureAfterNowFlights.forEach(System.out::println);

        System.out.println("\nПерелёты с сегментами, где прилёт происходит позже вылета:");
        List<Flight> arrivalAfterDepartureFlights = new FlightFilterImpl().arrivalBeforeDepartureFilter(flights);
        arrivalAfterDepartureFlights.forEach(System.out::println);

        System.out.println("\nПерелёты с временем на земле не более двух часов:");
        List<Flight> groundTimeWithinLimitFlights = new FlightFilterImpl().groundTimeExceedsFilter(flights);
        groundTimeWithinLimitFlights.forEach(System.out::println);
    }
}

