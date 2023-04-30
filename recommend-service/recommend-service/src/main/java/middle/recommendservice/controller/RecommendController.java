package middle.recommendservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.recommendservice.authentication.AuthenticationInfo;
import middle.recommendservice.controller.constant.ControllerLog;
import middle.recommendservice.controller.constant.RecommendUrl;
import middle.recommendservice.controller.restResponse.RestResponse;
import middle.recommendservice.dto.ImpressionRequest;
import middle.recommendservice.dto.RecommendResponse;
import middle.recommendservice.feignClient.ShopFeignService;
import middle.recommendservice.feignClient.constant.CircuitLog;
import middle.recommendservice.service.RecommendService;
import middle.recommendservice.validator.RecommendValidator;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RecommendController {

    private final RecommendService recommendService;
    private final ShopFeignService shopFeignService;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;
    private final AuthenticationInfo authenticationInfo;
    private final RecommendValidator recommendValidator;

    @GetMapping(RecommendUrl.MY_RECOMMEND)
    public ResponseEntity<?> myRecommend(HttpServletRequest request) {
        String username = authenticationInfo.getUsername(request);
        recommendValidator.validateRecommendNull(username);

        RecommendResponse recommend = recommendService.getRecommendByUsername(username);
        return ResponseEntity.ok(recommend);
    }

    @PostMapping(RecommendUrl.CREATE_RECOMMEND)
    public ResponseEntity<?> createRecommend(HttpServletRequest request) {
        String username = authenticationInfo.getUsername(request);
        recommendValidator.validateDuplicateRecommend(username);

        Long shopId = getShopByUsername(username);
        recommendValidator.validateShopNull(shopId);

        recommendService.createRecommend(shopId, username);
        log.info(ControllerLog.CREATE_RECOMMEND_SUCCESS.getValue());

        return RestResponse.createRecommendSuccess();
    }

    private Long getShopByUsername(String username) {
        return circuitBreakerFactory
                .create(CircuitLog.SHOP_CIRCUIT.getValue())
                .run(() -> shopFeignService.getShopByUsername(username),
                        throwable -> null
                );
    }

    @PutMapping(RecommendUrl.ADD_IMPRESSION)
    public ResponseEntity<?> addImpression(
            @RequestBody @Valid ImpressionRequest impressionRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        recommendValidator.validateBinding(bindingResult);

        String username = authenticationInfo.getUsername(request);
        recommendValidator.validateRecommendNull(username);

        recommendService.increaseImpression(impressionRequest, username);
        log.info(ControllerLog.INCREASE_IMPRESSION_SUCCESS.getValue());

        return RestResponse.increaseImpressionSuccess();
    }
}
