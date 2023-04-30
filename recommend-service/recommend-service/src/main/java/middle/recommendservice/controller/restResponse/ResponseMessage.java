package middle.recommendservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    DUPLICATE_RECOMMEND(400, "이미 등록되었습니다."),
    SHOP_IS_NULL(404, "상점이 존재하지 않습니다."),
    CREATE_RECOMMEND_SUCCESS(201, "추천 서비스에 성공적으로 등록했습니다."),
    RECOMMEND_IS_NULL(404, "등록되지 않았습니다."),
    INCREASE_IMPRESSION_SUCCESS(200, "노출횟수를 성공적으로 추가했습니다.");

    private final int status;
    private final String value;
}
