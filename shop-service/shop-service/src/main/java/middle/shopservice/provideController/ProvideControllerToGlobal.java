package middle.shopservice.provideController;

import lombok.RequiredArgsConstructor;
import middle.shopservice.provideController.constant.ProvideParamToGlobal;
import middle.shopservice.provideController.constant.ProvideUrlToGlobal;
import middle.shopservice.service.ShopService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProvideControllerToGlobal {

    private final ShopService shopService;

    @PostMapping(ProvideUrlToGlobal.RETURN_SHOP_BY_USERNAME)
    public Long returnShopUsername(@PathVariable(ProvideParamToGlobal.USERNAME) String username) {
        return shopService.getShopByUsernameForProvide(username);
    }
}
