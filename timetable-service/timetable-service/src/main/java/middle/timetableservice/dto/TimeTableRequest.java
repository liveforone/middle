package middle.timetableservice.dto;

import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeTableRequest {

    @Positive(message = "예약 시간을 반드시 기입해주세요.")
    private long reservationTime;
    private long reservationMinute;

    @Positive(message = "예약 가능한 수는 반드시 기입해주세요.")
    private long basicRemaining;
    private long remaining;
}
