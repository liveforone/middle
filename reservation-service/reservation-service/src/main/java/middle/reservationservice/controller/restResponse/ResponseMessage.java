package middle.reservationservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    RESERVATION_IS_NULL(404, "예약이 존재하지 않습니다."),
    USERNAME_NOT_MATCH(401, "일치하는 유저가 아닙니다."),
    RESERVE_FAIL(400, "잔여 정원을 초과되어 예약을 할 수 없습니다."),
    CANT_FIND_SHOP(404, "상점을 찾을 수 없습니다."),
    RESERVE_SUCCESS(201, "예약에 성공하였습니다.");

    private final int status;
    private final String value;
}
