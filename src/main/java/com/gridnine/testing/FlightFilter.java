package com.gridnine.testing;

import java.util.List;

interface FlightFilter {
    List<Flight> arrivalBeforeDepartureFilter(List<Flight> flights);
    List<Flight> departureBeforeNowFilter(List<Flight> flights);
    List<Flight> groundTimeExceedsFilter(List<Flight> flights);
}

