package middle.timetableservice.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTimeRequest {

    private long reservationHour;
    private long reservationMinute;
}
