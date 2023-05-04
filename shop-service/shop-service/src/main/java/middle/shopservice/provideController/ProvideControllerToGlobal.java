package middle.shopservice.provideController;

import lombok.RequiredArgsConstructor;
import middle.shopservice.service.ShopService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import static middle.shopservice.provideController.constant.ProvideParamToGlobal.*;
import static middle.shopservice.provideController.constant.ProvideUrlToGlobal.*;

@RestController
@RequiredArgsConstructor
public class ProvideControllerToGlobal {

    private final ShopService shopService;

    @PostMapping(RETURN_SHOP_BY_USERNAME)
    public Long returnShopUsername(@PathVariable(USERNAME) String username) {
        return shopService.getShopByUsernameForProvide(username);
    }
}
