package middle.shopservice.controller.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ControllerLog {

    CREATE_SHOP_SUCCESS("상점을 성공적으로 등록했습니다.");

    private final String value;
}
