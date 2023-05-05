package middle.recommendservice.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import static middle.recommendservice.feignClient.constant.ShopParam.*;
import static middle.recommendservice.feignClient.constant.ShopUrl.*;

@FeignClient(BASE)
public interface ShopFeignService {

    @PostMapping(RETURN_SHOP_BY_USERNAME)
    Long getShopByUsername(@PathVariable(USERNAME) String username);
}
