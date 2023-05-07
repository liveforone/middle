package middle.reservationservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.reservationservice.authentication.AuthenticationInfo;
import middle.reservationservice.controller.constant.ReservationParam;
import middle.reservationservice.dto.ReservationResponse;
import middle.reservationservice.service.ReservationService;
import middle.reservationservice.validator.ReservationValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static middle.reservationservice.controller.constant.ReservationUrl.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ReservationController {

    private final ReservationService reservationService;
    private final AuthenticationInfo authenticationInfo;
    private final ReservationValidator reservationValidator;

    @GetMapping(RESERVATION_DETAIL)
    public ResponseEntity<?> reservationDetail(
            @PathVariable(ReservationParam.ID) Long id,
            HttpServletRequest request
    ) {
        reservationValidator.validateReservationNull(id);
        reservationValidator.validateUsername(id, authenticationInfo.getUsername(request));

        ReservationResponse reservation = reservationService.getReservationById(id);
        return ResponseEntity.ok(reservation);
    }
}
