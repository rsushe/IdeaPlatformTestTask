import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        TicketParser ticketParser = new TicketParser();
        Optional<List<Ticket>> optionalTickets = ticketParser.parseFromFile("tickets.json");

        if (optionalTickets.isEmpty()) {
            System.out.print("Error reading tickets");
            return;
        }

        List<Ticket> tickets = optionalTickets.get();
        TicketAnalyzer ticketAnalyzer = new TicketAnalyzer(tickets);

        Map<String, Long> flightDurationByCarrier = ticketAnalyzer.findMinimumFlightDurationByCarrierBetweenCities(
                "Владивосток",
                ZoneId.of("Asia/Vladivostok"),
                "Тель-Авив",
                ZoneId.of("Asia/Tel_Aviv")
        );

        for (Map.Entry<String, Long> entry : flightDurationByCarrier.entrySet()) {
            long flightDuration = entry.getValue();
            long hours = flightDuration / 3600;
            long minutes = (flightDuration % 3600) / 60;
            long seconds = flightDuration % 60;

            System.out.printf(
                    "Minimum flight time between Vladivostok and Tel-Aviv for carrier %s is equal to %02d:%02d:%02d\n",
                    entry.getKey(),
                    hours,
                    minutes,
                    seconds
            );
        }

        double difference = ticketAnalyzer.getDifferenceBetweenAverageAndMedianFlightPriceBetweenCities(
                "Владивосток",
                "Тель-Авив"
        );
        System.out.printf(
                "\nDifference between average and median flight price between Vladivostok and Tel-Aviv is equal to %.3f",
                difference
        );
    }
}
