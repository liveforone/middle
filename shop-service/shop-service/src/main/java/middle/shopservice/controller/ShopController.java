package middle.shopservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.shopservice.authentication.AuthenticationInfo;
import middle.shopservice.controller.constant.ParamConstant;
import middle.shopservice.controller.constant.ShopUrl;
import middle.shopservice.controller.restResponse.RestResponse;
import middle.shopservice.dto.ShopResponse;
import middle.shopservice.service.ShopService;
import middle.shopservice.validator.ShopValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ShopController {

    private final ShopService shopService;
    public final AuthenticationInfo authenticationInfo;
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

    @GetMapping(ShopUrl.MY_SHOP)
    public ResponseEntity<?> myShop(HttpServletRequest request) {
        String auth = authenticationInfo.getAuth(request);

        if (shopValidator.isNotOwner(auth)) {
            return RestResponse.authIsNotOwner();
        }

        String username = authenticationInfo.getUsername(request);
        if (shopValidator.isNull(username)) {
            return RestResponse.shopIsNull();
        }

        ShopResponse shop = shopService.getShopByUsername(username);
        return ResponseEntity.ok(shop);
    }

    @GetMapping(ShopUrl.HOME_PAGE)
    public ResponseEntity<?> homePage(
            @RequestParam(name = ParamConstant.LAST_ID) Long lastId,
            @RequestParam(name = ParamConstant.PAGE_SIZE) int pageSize
    ) {
        List<ShopResponse> shop = shopService.getHomePage(lastId, pageSize);
        return ResponseEntity.ok(shop);
    }

    @GetMapping(ShopUrl.SEARCH_SHOP_NAME)
    public ResponseEntity<?> searchByShopName(
            @PathVariable(name = ParamConstant.SHOP_NAME) String shopName,
            @RequestParam(name = ParamConstant.LAST_ID) Long lastId,
            @RequestParam(name = ParamConstant.PAGE_SIZE) int pageSize
    ) {
        List<ShopResponse> shop = shopService.searchShopNamePage(shopName, lastId, pageSize);
        return ResponseEntity.ok(shop);
    }

    @GetMapping(ShopUrl.SEARCH_CITY)
    public ResponseEntity<?> searchByCity(
            @PathVariable(name = ParamConstant.CITY) String city,
            @RequestParam(name = ParamConstant.LAST_ID) Long lastId,
            @RequestParam(name = ParamConstant.PAGE_SIZE) int pageSize
    ) {
        List<ShopResponse> shop = shopService.searchCityPage(city, lastId, pageSize);
        return ResponseEntity.ok(shop);
    }



    //상정 생성시 auth owner인지 판별
}
