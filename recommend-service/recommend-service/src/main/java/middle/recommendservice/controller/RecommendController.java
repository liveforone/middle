package middle.recommendservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.recommendservice.feignClient.ShopFeignService;
import middle.recommendservice.feignClient.constant.CircuitLog;
import middle.recommendservice.service.RecommendService;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecommendController {

    private final RecommendService recommendService;
    private final ShopFeignService shopFeignService;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    private Long getShopByUsername(String username) {
        return circuitBreakerFactory
                .create(CircuitLog.SHOP_CIRCUIT.getValue())
                .run(() -> shopFeignService.getShopByUsername(username),
                        throwable -> null
                );
    }
}
