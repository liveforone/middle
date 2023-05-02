package middle.timetableservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.timetableservice.authentication.AuthenticationInfo;
import middle.timetableservice.controller.constant.ControllerLog;
import middle.timetableservice.controller.constant.ParamConstant;
import middle.timetableservice.controller.constant.TimetableUrl;
import middle.timetableservice.controller.restResponse.RestResponse;
import middle.timetableservice.dto.TimetableRequest;
import middle.timetableservice.dto.TimetableResponse;
import middle.timetableservice.dto.UpdateTimeRequest;
import middle.timetableservice.feignClient.ShopFeignService;
import middle.timetableservice.feignClient.constant.CircuitLog;
import middle.timetableservice.service.TimetableService;
import middle.timetableservice.validator.TimetableValidator;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class TimetableController {

    private final TimetableService timetableService;
    private final TimetableValidator timetableValidator;
    private final AuthenticationInfo authenticationInfo;
    private final ShopFeignService shopFeignService;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    @GetMapping(TimetableUrl.TIMETABLE_PAGE_BY_SHOP)
    public ResponseEntity<?> timetablesPage(
            @PathVariable(ParamConstant.SHOP_ID) Long shopId,
            @RequestParam(name = ParamConstant.LAST_ID, defaultValue = ParamConstant.DEFAULT_ID) Long lastId
    ) {
        List<TimetableResponse> timetables = timetableService.getTimetablesByShopId(shopId, lastId);

        return ResponseEntity.ok(timetables);
    }

    @GetMapping(TimetableUrl.TIMETABLE_DETAIL)
    public ResponseEntity<?> timetableDetail(
            @PathVariable(ParamConstant.ID) Long id
    ) {
        timetableValidator.validateTimetableNull(id);

        TimetableResponse timetable = timetableService.getTimetableById(id);
        return ResponseEntity.ok(timetable);
    }

    @PostMapping(TimetableUrl.CREATE_TIMETABLE)
    public ResponseEntity<?> createTimetable(
            @RequestBody @Valid TimetableRequest timetableRequest,
            BindingResult bindingResult,
            @PathVariable(ParamConstant.SHOP_ID) Long shopId,
            HttpServletRequest request
    ) {
        timetableValidator.validateAuth(authenticationInfo.getAuth(request));

        String username = authenticationInfo.getUsername(request);
        Long foundShopId = getShopByUsername(username);
        timetableValidator.validateShop(foundShopId, shopId);
        timetableValidator.validateBinding(bindingResult);

        timetableService.createTimetable(shopId, username, timetableRequest);
        log.info(ControllerLog.CREATE_TIMETABLE_SUCCESS.getValue());

        return RestResponse.createTimetableSuccess();
    }

    private Long getShopByUsername(String username) {
        return circuitBreakerFactory
                .create(CircuitLog.SHOP_CIRCUIT.getValue())
                .run(
                        () -> shopFeignService.getShopByUsername(username),
                        throwable -> null
                );
    }

    @PutMapping(TimetableUrl.UPDATE_TIME)
    public ResponseEntity<?> updateTime(
            @PathVariable(ParamConstant.ID) Long id,
            @RequestBody UpdateTimeRequest updateTimeRequest,
            HttpServletRequest request
    ) {
        timetableValidator.validateTimetableNullAndOwner(id, authenticationInfo.getUsername(request));

        timetableService.updateTime(updateTimeRequest, id);
        log.info(ControllerLog.UPDATE_TIME_SUCCESS.getValue());

        return RestResponse.updateTimeSuccess();
    }


    //삭제
}
