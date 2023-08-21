import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@ToString
public class Ticket {
    private String origin;
    private String originName;
    private String destination;
    private String destinationName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy")
    private LocalDate departureDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "H:mm")
    private LocalTime departureTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy")
    private LocalDate arrivalDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "H:mm")
    private LocalTime arrivalTime;
    private String carrier;
    private int stops;
    private int price;
}
