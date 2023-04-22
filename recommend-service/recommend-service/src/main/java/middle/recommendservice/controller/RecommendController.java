package middle.recommendservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.recommendservice.authentication.AuthenticationInfo;
import middle.recommendservice.controller.constant.ControllerLog;
import middle.recommendservice.controller.constant.RecommendUrl;
import middle.recommendservice.controller.restResponse.RestResponse;
import middle.recommendservice.feignClient.ShopFeignService;
import middle.recommendservice.feignClient.constant.CircuitLog;
import middle.recommendservice.service.RecommendService;
import middle.recommendservice.utility.CommonUtils;
import middle.recommendservice.validator.RecommendValidator;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecommendController {

    private final RecommendService recommendService;
    private final ShopFeignService shopFeignService;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;
    private final AuthenticationInfo authenticationInfo;
    private final RecommendValidator recommendValidator;

    private Long getShopByUsername(String username) {
        return circuitBreakerFactory
                .create(CircuitLog.SHOP_CIRCUIT.getValue())
                .run(() -> shopFeignService.getShopByUsername(username),
                        throwable -> null
                );
    }

    @PostMapping(RecommendUrl.CREATE_RECOMMEND)
    public ResponseEntity<?> createRecommend(
            HttpServletRequest request
    ) {
        String username = authenticationInfo.getUsername(request);
        if (recommendValidator.isDuplicateRecommend(username)) {
            return RestResponse.duplicateRecommend();
        }

        Long shopId = getShopByUsername(username);
        if (CommonUtils.isNull(shopId)) {
            return RestResponse.shopIsNull();
        }

        recommendService.createRecommend(shopId, username);
        log.info(ControllerLog.CREATE_RECOMMEND_SUCCESS.getValue());

        return RestResponse.createRecommendSuccess();
    }

    //switch 문에서 하나씩 검증해보고, 만약에 없는 값이라서 default까지 가면 badrequest 날려야함
}
