package middle.reservationservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    RESERVE_SUCCESS("예약에 성공하였습니다.");

    private final String value;
}
