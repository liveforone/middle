package middle.timetableservice.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimetableRequest {

    @Positive(message = "예약 시간을 반드시 기입해주세요.")
    private long reservationHour;
    private long reservationMinute;

    @Positive(message = "예약 가능한 수는 반드시 기입해주세요.")
    private long basicRemaining;
}
