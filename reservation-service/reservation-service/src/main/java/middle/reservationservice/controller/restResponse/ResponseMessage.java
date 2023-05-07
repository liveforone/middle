package middle.reservationservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    RESERVATION_IS_NULL(404, "예약이 존재하지 않습니다."),

    RESERVE_SUCCESS(201, "예약에 성공하였습니다.");

    private final int status;
    private final String value;
}
