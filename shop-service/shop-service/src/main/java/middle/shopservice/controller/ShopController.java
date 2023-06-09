package middle.shopservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.shopservice.authentication.AuthenticationInfo;
import middle.shopservice.controller.constant.ControllerLog;
import middle.shopservice.controller.restResponse.RestResponse;
import middle.shopservice.dto.ShopRequest;
import middle.shopservice.dto.ShopResponse;
import middle.shopservice.dto.updateShop.UpdateAddressRequest;
import middle.shopservice.dto.updateShop.UpdateNameRequest;
import middle.shopservice.dto.updateShop.UpdateTelRequest;
import middle.shopservice.service.ShopService;
import middle.shopservice.validator.ShopValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static middle.shopservice.controller.constant.ShopParam.*;
import static middle.shopservice.controller.constant.ShopUrl.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ShopController {

    private final ShopService shopService;
    public final AuthenticationInfo authenticationInfo;
    private final ShopValidator shopValidator;

    @GetMapping(SHOP_DETAIL)
    public ResponseEntity<?> shopDetail(
            @PathVariable(SHOP_ID) Long shopId
    ) {
        shopValidator.validateShopNull(shopId);

        ShopResponse shop = shopService.getShopById(shopId);
        return ResponseEntity.ok(shop);
    }

    @GetMapping(MY_SHOP)
    public ResponseEntity<?> myShop(HttpServletRequest request) {
        String auth = authenticationInfo.getAuth(request);
        shopValidator.validateAuth(auth);

        String username = authenticationInfo.getUsername(request);
        shopValidator.validateShopNull(username);

        ShopResponse shop = shopService.getShopByUsername(username);
        return ResponseEntity.ok(shop);
    }

    @GetMapping(HOME_PAGE)
    public ResponseEntity<?> homePage(
            @RequestParam(name = LAST_ID) Long lastId
    ) {
        List<ShopResponse> shop = shopService.getHomePage(lastId);
        return ResponseEntity.ok(shop);
    }

    @GetMapping(SEARCH_SHOP)
    public ResponseEntity<?> searchByShop(
            @RequestParam(name = CITY, required = false) String city,
            @RequestParam(name = STREET, required = false) String street,
            @RequestParam(name = SHOP_NAME, required = false) String shopName,
            @RequestParam(name = LAST_ID, defaultValue = DEFAULT_ID) Long lastId
    ) {
        List<ShopResponse> shop = shopService.searchShop(city, street, shopName, lastId);
        return ResponseEntity.ok(shop);
    }

    @PostMapping(CREATE_SHOP)
    public ResponseEntity<?> createShop(
            @RequestBody @Valid ShopRequest shopRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        String auth = authenticationInfo.getAuth(request);
        shopValidator.validateAuth(auth);

        String username = authenticationInfo.getUsername(request);
        shopValidator.validateDuplicateOwner(username);
        shopValidator.validateBinding(bindingResult);

        shopService.createShop(shopRequest, username);
        log.info(ControllerLog.CREATE_SHOP_SUCCESS.getValue());

        return RestResponse.createShopSuccess();
    }

    @PutMapping(UPDATE_SHOP_NAME)
    public ResponseEntity<?> updateShopName(
            @RequestBody @Valid UpdateNameRequest updateNameRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        String auth = authenticationInfo.getAuth(request);
        shopValidator.validateAuth(auth);

        String username = authenticationInfo.getUsername(request);
        shopValidator.validateShopNull(username);
        shopValidator.validateBinding(bindingResult);

        shopService.updateShopName(updateNameRequest, username);
        log.info(ControllerLog.UPDATE_NAME_SUCCESS.getValue() + username);

        return RestResponse.updateNameSuccess();
    }

    @PutMapping(UPDATE_SHOP_TEL)
    public ResponseEntity<?> updateTel(
            @RequestBody @Valid UpdateTelRequest updateTelRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        String auth = authenticationInfo.getAuth(request);
        shopValidator.validateAuth(auth);

        String username = authenticationInfo.getUsername(request);
        shopValidator.validateShopNull(username);
        shopValidator.validateBinding(bindingResult);

        shopService.updateTel(updateTelRequest, username);
        log.info(ControllerLog.UPDATE_TEL_SUCCESS.getValue() + username);

        return RestResponse.updateTelSuccess();
    }

    @PutMapping(UPDATE_SHOP_ADDRESS)
    public ResponseEntity<?> updateAddress(
            @RequestBody @Valid UpdateAddressRequest updateAddressRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        String auth = authenticationInfo.getAuth(request);
        shopValidator.validateAuth(auth);

        String username = authenticationInfo.getUsername(request);
        shopValidator.validateShopNull(username);
        shopValidator.validateBinding(bindingResult);

        shopService.updateAddress(updateAddressRequest, username);
        log.info(ControllerLog.UPDATE_ADDRESS_SUCCESS.getValue() + username);

        return RestResponse.updateAddressSuccess();
    }
}
