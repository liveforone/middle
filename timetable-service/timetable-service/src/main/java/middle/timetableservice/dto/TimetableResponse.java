package middle.timetableservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimetableResponse {

    private Long id;
    private long reservationHour;
    private long reservationMinute;
    private long remaining;
}
