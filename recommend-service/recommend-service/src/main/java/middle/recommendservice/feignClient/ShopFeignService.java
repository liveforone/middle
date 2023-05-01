package middle.recommendservice.feignClient;

import middle.recommendservice.feignClient.constant.ShopParamConstant;
import middle.recommendservice.feignClient.constant.ShopUrl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(ShopUrl.BASE)
public interface ShopFeignService {

    @PostMapping(ShopUrl.RETURN_SHOP_BY_USERNAME)
    Long getShopByUsername(@PathVariable(ShopParamConstant.USERNAME) String username);
}
