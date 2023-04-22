package middle.recommendservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    CREATE_RECOMMEND_SUCCESS("추천 서비스에 성공적으로 등록했습니다.");

    private final String value;
}
