package middle.reservationservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.reservationservice.authentication.AuthenticationInfo;
import middle.reservationservice.controller.constant.ControllerLog;
import middle.reservationservice.controller.restResponse.RestResponse;
import middle.reservationservice.dto.ReservationResponse;
import middle.reservationservice.feignClient.TimetableFeignService;
import middle.reservationservice.feignClient.constant.CircuitLog;
import middle.reservationservice.service.ReservationService;
import middle.reservationservice.validator.ReservationValidator;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static middle.reservationservice.controller.constant.ReservationParam.*;
import static middle.reservationservice.controller.constant.ReservationUrl.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;
    private final AuthenticationInfo authenticationInfo;
    private final ReservationValidator reservationValidator;
    private final TimetableFeignService timetableFeignService;
    private final CircuitBreakerFactory<?, ?> circuitBreakerFactory;

    @GetMapping(RESERVATION_DETAIL)
    public ResponseEntity<?> reservationDetail(
            @PathVariable(ID) Long id,
            HttpServletRequest request
    ) {
        reservationValidator.validateReservationNull(id);
        reservationValidator.validateUsername(id, authenticationInfo.getUsername(request));

        ReservationResponse reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping(MY_RESERVATION)
    public ResponseEntity<List<ReservationResponse>> myReservation(
            @RequestParam(name = LAST_ID, defaultValue = DEFAULT_ID) Long lastId,
            HttpServletRequest request
    ) {
        List<ReservationResponse> reservations = reservationService
                .getReservationsByUsername(authenticationInfo.getUsername(request), lastId);
        return ResponseEntity.ok(reservations);
    }

    @GetMapping(RESERVATION_BELONG_SHOP)
    public ResponseEntity<List<ReservationResponse>> reservationsBelongToShop(
            @PathVariable(SHOP_ID) Long shopId,
            @RequestParam(name = LAST_ID, defaultValue = DEFAULT_ID) Long lastId
    ) {
        List<ReservationResponse> reservations = reservationService.getReservationsByShopId(shopId, lastId);
        return ResponseEntity.ok(reservations);
    }

    @PostMapping(RESERVE)
    public ResponseEntity<?> reserve(
            @PathVariable(TIMETABLE_ID) Long timetableId,
            HttpServletRequest request
    ) {
        reservationValidator.validateReserveTimetable(reserveTimetable(timetableId));

        Long shopId = getShopId(timetableId);
        reservationValidator.validateShop(shopId);

        reservationService.reserve(
                timetableId,
                authenticationInfo.getUsername(request),
                shopId
        );
        log.info(ControllerLog.RESERVE_SUCCESS.getValue());

        return RestResponse.reserveSuccess();
    }

    private boolean reserveTimetable(Long timetableId) {
        return circuitBreakerFactory
                .create(CircuitLog.TIMETABLE_CIRCUIT_LOG.getValue())
                .run(
                        ()-> timetableFeignService.reserveTimetable(timetableId),
                        throwable -> false
                );
    }

    private Long getShopId(Long timetableId) {
        return circuitBreakerFactory
                .create(CircuitLog.TIMETABLE_CIRCUIT_LOG.getValue())
                .run(
                        ()-> timetableFeignService.getShopId(timetableId),
                        throwable -> null
                );
    }
}
