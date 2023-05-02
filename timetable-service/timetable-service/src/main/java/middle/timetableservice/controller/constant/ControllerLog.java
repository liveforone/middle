package middle.timetableservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    CREATE_TIMETABLE_SUCCESS("타임테이블을 성공적으로 등록했습니다."),
    UPDATE_TIME_SUCCESS("시간을 성공적으로 업데이트 했습니다."),
    DELETE_TIMETABLE_SUCCESS("타임테이블을 성공적으로 삭제했습니다. | ID : ");

    private final String value;
}
