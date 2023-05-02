package middle.timetableservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    TIMETABLE_IS_NULL(404, "타임테이블이 존재하지 않습니다."),
    AUTH_IS_NOT_OWNER(403, "가게 주인 회원만 타임테이블 등록이 가능합니다."),
    SHOP_IS_NULL(404, "상점이 존재하지 않습니다."),
    NOT_OWNER_OF_SHOP(400, "상점의 주인이 아닙니다."),
    CREATE_TIMETABLE_SUCCESS(201, "타임테이블 생성을 성공적으로 완료했습니다."),
    UPDATE_TIME_SUCCESS(200, "시간을 성공적으로 업데이트 했습니다."),
    DELETE_TIMETABLE_SUCCESS(200, "타임테이블을 성공적으로 삭제했습니다.");

    private final int status;
    private final String value;
}
