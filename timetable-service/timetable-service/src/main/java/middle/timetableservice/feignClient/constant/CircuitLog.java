package middle.timetableservice.feignClient.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CircuitLog {

    SHOP_CIRCUIT("shop-circuit");

    private final String value;
}
