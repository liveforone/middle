package middle.recommendservice.controller;

import lombok.RequiredArgsConstructor;
import middle.recommendservice.algorithm.RecommendAlgorithm;
import middle.recommendservice.service.RecommendService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RecommendProvideController {

    private final RecommendAlgorithm recommendAlgorithm;
    private final RecommendService recommendService;

    @GetMapping(ProvideUrl.RECOMMEND_SHOP)
    public Long provideRecommendShopId() {
        Long foundShopId = recommendAlgorithm.recommendShop();
        recommendService.decreaseImpression(foundShopId);

        return foundShopId;
    }
}