package com.gridnine.testing;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

class FlightFilterImpl implements FlightFilter {
    // Максимально допустимое время на земле между сегментами
    private static final Duration MAX_GROUND_TIME = Duration.ofHours(2);

    @Override
    public List<Flight> groundTimeExceedsFilter(List<Flight> flights) {
        // Фильтруем перелёты, у которых общее время на земле не превышает MAX_GROUND_TIME
        return flights.stream()
                .filter(flight -> {
                    // Получаем список сегментов данного перелёта
                    List<Segment> segments = flight.getSegments();
                    int totalGroundTime = 0;
                    // Рассчитываем общее время на земле между каждым сегментом
                    for (int i = 0; i < segments.size() - 1; i++) {
                        // Рассчитываем время на земле между прилётом текущего сегмента и вылетом следующего
                        Duration groundTime = Duration.between(segments.get(i).getArrivalDate(), segments.get(i + 1).getDepartureDate());
                        // Добавляем время на земле в общую сумму (в минутах)
                        totalGroundTime += (int) groundTime.toMinutes();
                    }
                    // Возвращаем true, если общее время на земле не превышает допустимого
                    return Duration.ofMinutes(totalGroundTime).compareTo(MAX_GROUND_TIME) <= 0;
                })
                // Собираем результаты фильтрации в список
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> arrivalBeforeDepartureFilter(List<Flight> flights) {
        // Фильтруем перелёты, в которых нет сегментов, где время прилёта раньше времени вылета
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .noneMatch(segment -> segment.getArrivalDate().isBefore(segment.getDepartureDate())))
                // Собираем результаты фильтрации в список
                .collect(Collectors.toList());
    }

    @Override
    public List<Flight> departureBeforeNowFilter(List<Flight> flights) {
        // Текущее время
        LocalDateTime now = LocalDateTime.now();
        // Фильтруем перелёты, у которых все сегменты вылетают после текущего времени
        return flights.stream()
                .filter(flight -> flight.getSegments().stream()
                        .allMatch(segment -> segment.getDepartureDate().isAfter(now)))
                // Собираем результаты фильтрации в список
                .collect(Collectors.toList());
    }
}

