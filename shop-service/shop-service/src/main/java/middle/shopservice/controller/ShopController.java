package middle.shopservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.shopservice.authentication.AuthenticationInfo;
import middle.shopservice.controller.constant.ControllerLog;
import middle.shopservice.controller.constant.ParamConstant;
import middle.shopservice.controller.constant.ShopUrl;
import middle.shopservice.controller.restResponse.RestResponse;
import middle.shopservice.dto.ShopRequest;
import middle.shopservice.dto.ShopResponse;
import middle.shopservice.service.ShopService;
import middle.shopservice.validator.ShopValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(ShopUrl.SEARCH_STREET)
    public ResponseEntity<?> searchByStreet(
            @PathVariable(name = ParamConstant.STREET) String street,
            @RequestParam(name = ParamConstant.LAST_ID) Long lastId,
            @RequestParam(name = ParamConstant.PAGE_SIZE) int pageSize
    ) {
        List<ShopResponse> shop = shopService.searchStreetPage(street, lastId, pageSize);
        return ResponseEntity.ok(shop);
    }

    @PostMapping(ShopUrl.CREATE_SHOP)
    public ResponseEntity<?> createShop(
            @RequestBody @Valid ShopRequest shopRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        String auth = authenticationInfo.getAuth(request);
        if (shopValidator.isNotOwner(auth)) {
            return RestResponse.authIsNotOwner();
        }

        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        String username = authenticationInfo.getUsername(request);
        shopService.createShop(shopRequest, username);
        log.info(ControllerLog.CREATE_SHOP_SUCCESS.getValue());

        return RestResponse.createShopSuccess();
    }
}
