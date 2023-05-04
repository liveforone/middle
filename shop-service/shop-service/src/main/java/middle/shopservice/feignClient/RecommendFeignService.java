package middle.shopservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import static middle.shopservice.feignClient.constant.RecommendUrl.*;

@FeignClient(name = BASE)
public interface RecommendFeignService {

    @GetMapping(RECOMMEND_SHOP)
    Long getRecommendShopId();
}
