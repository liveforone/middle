package middle.timetableservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    CREATE_TIMETABLE_SUCCESS("타임테이블을 성공적으로 등록했습니다.");

    private final String value;
}
