package middle.shopservice.feignClient.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CircuitLog {

    SHOP_CIRCUIT_LOG("shop-circuit-log");

    private final String value;
}
