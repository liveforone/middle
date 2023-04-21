package middle.shopservice.controller;

import lombok.RequiredArgsConstructor;
import middle.shopservice.controller.constant.ParamConstant;
import middle.shopservice.controller.constant.ShopUrl;
import middle.shopservice.dto.ShopResponse;
import middle.shopservice.service.ShopService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShopProvideController {

    private final ShopService shopService;

    @PostMapping(ShopUrl.RETURN_SHOP_USERNAME)
    public Long returnShopUsername(@PathVariable(ParamConstant.USERNAME) String username) {
        return shopService.getShopByUsernameForProvide(username);
    }
}
