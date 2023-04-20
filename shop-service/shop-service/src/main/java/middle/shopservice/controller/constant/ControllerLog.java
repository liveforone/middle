package middle.shopservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    CREATE_SHOP_SUCCESS("상점을 성공적으로 등록했습니다."),
    UPDATE_NAME_SUCCESS("상호를 성공적으로 수정했습니다. | Username : "),
    UPDATE_TEL_SUCCESS("전화번호를 성공적으로 수정했습니다. | Username : "),
    UPDATE_ADDRESS_SUCCESS("주소를 성공적으로 수정했습니다. | Username : ");

    private final String value;
}
