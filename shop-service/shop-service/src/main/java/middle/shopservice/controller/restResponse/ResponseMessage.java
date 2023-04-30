package middle.shopservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    SHOP_IS_NULL(404, "존재하지 않는 상점입니다."),
    AUTH_IS_NOT_OWNER(403, "가게 주인만 접근 가능합니다."),
    DUPLICATE_OWNER(400, "이미 등록된 회원입니다."),
    CREATE_SHOP_SUCCESS(201, "상점을 성공적으로 등록했습니다."),
    UPDATE_NAME_SUCCESS(200, "상호를 성공적으로 수정했습니다."),
    UPDATE_TEL_SUCCESS(200, "전화번호를 성공적으로 수정했습니다."),
    UPDATE_ADDRESS_SUCCESS(200, "주소를 성공적으로 수정했습니다.");

    private final int status;
    private final String value;
}
