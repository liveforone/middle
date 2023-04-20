package middle.shopservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestMessage {

    SHOP_IS_NULL("존재하지 않는 상점입니다."),
    AUTH_IS_NOT_OWNER("가게 주인만 접근 가능합니다."),
    CREATE_SHOP_SUCCESS("상점을 성공적으로 등록했습니다."),
    UPDATE_NAME_SUCCESS("상호를 성공적으로 수정했습니다."),
    UPDATE_TEL_SUCCESS("전화번호를 성공적으로 수정했습니다.");

    private final String value;
}
