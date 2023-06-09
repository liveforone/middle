package middle.reservationservice.controller.restResponse;

import org.springframework.http.ResponseEntity;

public class RestResponse {

    public static ResponseEntity<?> reserveSuccess() {
        return ResponseEntity
                .status(ResponseMessage.RESERVE_SUCCESS.getStatus())
                .body(ResponseMessage.RESERVE_SUCCESS.getValue());
    }

    public static ResponseEntity<?> cancelSuccess() {
        return ResponseEntity.ok(ResponseMessage.CANCEL_SUCCESS.getValue());
    }
}
