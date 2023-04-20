package middle.shopservice.controller.restResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Objects;

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

    public static ResponseEntity<?> validError(BindingResult bindingResult) {
        String errorMessage = Objects
                .requireNonNull(bindingResult.getFieldError())
                .getDefaultMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    public static ResponseEntity<?> createShopSuccess() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RestMessage.CREATE_SHOP_SUCCESS.getValue());
    }
}
