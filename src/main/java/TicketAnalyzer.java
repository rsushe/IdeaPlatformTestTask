import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class TicketAnalyzer {
    private List<Ticket> tickets;

    public Map<String, Long> findMinimumFlightDurationByCarrierBetweenCities(
            String originName, ZoneId originZoneId,
            String destinationName, ZoneId destinationZoneId
    ) {
        Map<String, Long> flightDurationByCarrier = new HashMap<>();
        for (Ticket ticket : tickets) {
            if (ticket.getOriginName().equals(originName) && ticket.getDestinationName().equals(destinationName)) {
                LocalDateTime departureTime = LocalDateTime.of(ticket.getDepartureDate(), ticket.getDepartureTime());
                ZonedDateTime departureZonedTime = ZonedDateTime.of(departureTime, originZoneId);

                LocalDateTime arrivalTime = LocalDateTime.of(ticket.getArrivalDate(), ticket.getArrivalTime());
                ZonedDateTime arrivalZonedTime = ZonedDateTime.of(arrivalTime, destinationZoneId);

                Long flightDuration = ChronoUnit.SECONDS.between(departureZonedTime, arrivalZonedTime);
                flightDurationByCarrier.merge(ticket.getCarrier(), flightDuration, Math::min);
            }
        }

        return flightDurationByCarrier;
    }

    public double getDifferenceBetweenAverageAndMedianFlightPriceBetweenCities(
            String originName,
            String destinationName
    ) {
        List<Integer> flightPrices = tickets
                .stream()
                .filter(ticket -> ticket.getOriginName().equals(originName)
                        && ticket.getDestinationName().equals(destinationName))
                .map(Ticket::getPrice)
                .toList();

        double averageFlightPrice = flightPrices.stream().mapToDouble(price -> price).average().getAsDouble();
        double medianFlightPrice = findMedian(flightPrices);

        return averageFlightPrice - medianFlightPrice;
    }

    private double findMedian(List<Integer> flightPrices) {
        List<Integer> flightPricesCopy = new ArrayList<>(flightPrices);
        Collections.sort(flightPricesCopy);

        int listSize = flightPricesCopy.size();
        double median;
        if (listSize % 2 == 0) {
            median = (flightPricesCopy.get(listSize / 2) + flightPricesCopy.get(listSize / 2 - 1)) / 2.0;
        } else {
            median = flightPricesCopy.get(listSize / 2);
        }

        return median;
    }
}
