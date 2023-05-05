package middle.reservationservice.feignClient.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CircuitLog {

    TIMETABLE_CIRCUIT_LOG("timetable-circuit");

    private final String value;
}
