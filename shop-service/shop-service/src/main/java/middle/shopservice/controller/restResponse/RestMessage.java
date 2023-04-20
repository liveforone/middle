package middle.shopservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestMessage {

    SHOP_IS_NULL("존재하지 않는 상점입니다.");

    private final String value;
}
