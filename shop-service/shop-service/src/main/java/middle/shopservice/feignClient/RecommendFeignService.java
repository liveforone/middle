package middle.shopservice.feignClient;

import middle.shopservice.feignClient.constant.RecommendUrl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = RecommendUrl.BASE)
public interface RecommendFeignService {

    @GetMapping(RecommendUrl.RECOMMEND_SHOP)
    Long getRecommendShopId();
}
