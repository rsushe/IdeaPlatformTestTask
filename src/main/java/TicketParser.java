import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketParser {
    private final ObjectMapper mapper;

    public TicketParser() {
        mapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    public Optional<List<Ticket>> parseFromFile(String fileName) {
        InputStream ticketsStream = Main.class.getClassLoader().getResourceAsStream(fileName);
        List<Ticket> tickets = new ArrayList<>();

        try {
            JsonNode jsonNode = mapper.readTree(ticketsStream);

            for (JsonNode node : jsonNode.get("tickets")) {
                tickets.add(mapper.readValue(node.toString(), Ticket.class));
            }
        } catch (IOException e) {
            return Optional.empty();
        }

        return Optional.of(tickets);
    }
}
