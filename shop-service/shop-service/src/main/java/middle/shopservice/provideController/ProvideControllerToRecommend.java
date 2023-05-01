package middle.shopservice.provideController;

import lombok.RequiredArgsConstructor;
import middle.shopservice.provideController.constant.ProvideParamToRecommend;
import middle.shopservice.provideController.constant.ProvideUrlToRecommend;
import middle.shopservice.service.ShopService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProvideControllerToRecommend {

    private final ShopService shopService;

    @PostMapping(ProvideUrlToRecommend.RETURN_SHOP_USERNAME)
    public Long returnShopUsername(@PathVariable(ProvideParamToRecommend.USERNAME) String username) {
        return shopService.getShopByUsernameForProvide(username);
    }
}
