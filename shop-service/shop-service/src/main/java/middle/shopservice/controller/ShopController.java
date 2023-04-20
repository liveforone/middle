package middle.shopservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.shopservice.controller.constant.ParamConstant;
import middle.shopservice.controller.constant.ShopUrl;
import middle.shopservice.controller.restResponse.RestResponse;
import middle.shopservice.dto.ShopResponse;
import middle.shopservice.service.ShopService;
import middle.shopservice.validator.ShopValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ShopController {

    private final ShopService shopService;
    private final ShopValidator shopValidator;

    @GetMapping(ShopUrl.SHOP_DETAIL)
    public ResponseEntity<?> shopDetail(
            @PathVariable(ParamConstant.SHOP_ID) Long shopId
    ) {
        if (shopValidator.isNull(shopId)) {
            return RestResponse.shopIsNull();
        }

        ShopResponse shop = shopService.getShopById(shopId);
        return ResponseEntity.ok(shop);
    }
    
    //상정 생성시 auth owner인지 판별
}
