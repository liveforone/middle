package middle.shopservice.controller.restResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestResponse {

    public static ResponseEntity<?> shopIsNull() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestMessage.SHOP_IS_NULL.getValue());
    }

    public static ResponseEntity<?> authIsNotOwner() {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(RestMessage.AUTH_IS_NOT_OWNER.getValue());
    }
}
