package middle.timetableservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    TIMETABLE_IS_NULL(404, "타임테이블이 존재하지 않습니다."),
    CREATE_TIMETABLE_SUCCESS(201, "타임테이블 생성을 성공적으로 완료했습니다.");

    private final int status;
    private final String value;
}
