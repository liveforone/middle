package middle.reservationservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    RESERVE_SUCCESS(201, "예약에 성공하였습니다.");

    private final int status;
    private final String value;
}
