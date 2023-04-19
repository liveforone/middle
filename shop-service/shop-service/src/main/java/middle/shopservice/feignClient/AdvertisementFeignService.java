package middle.shopservice.feignClient;

import middle.shopservice.feignClient.constant.AdvertisementUrl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = AdvertisementUrl.BASE)
public interface AdvertisementFeignService {

    @GetMapping(AdvertisementUrl.DRAW_SHOP)
    Long getDrawShopId();
}
