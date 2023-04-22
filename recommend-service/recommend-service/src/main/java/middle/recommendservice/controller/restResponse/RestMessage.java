package middle.recommendservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestMessage {

    DUPLICATE_RECOMMEND("이미 등록되었습니다."),
    SHOP_IS_NULL("상점이 존재하지 않습니다."),
    CREATE_RECOMMEND_SUCCESS("추천 서비스에 성공적으로 등록했습니다."),
    RECOMMEND_IS_NULL("등록되지 않았습니다.");

    private final String value;
}
