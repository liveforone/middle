package middle.recommendservice.controller.restResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Objects;

public class RestResponse {

    public static ResponseEntity<?> duplicateRecommend() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestMessage.DUPLICATE_RECOMMEND.getValue());
    }

    public static ResponseEntity<?> shopIsNull() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(RestMessage.SHOP_IS_NULL.getValue());
    }

    public static ResponseEntity<?> createRecommendSuccess() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(RestMessage.CREATE_RECOMMEND_SUCCESS.getValue());
    }

    public static ResponseEntity<?> recommendIsNull() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(RestMessage.RECOMMEND_IS_NULL.getValue());
    }

    public static ResponseEntity<?> validError(BindingResult bindingResult) {
        String errorMessage = Objects
                .requireNonNull(bindingResult.getFieldError())
                .getDefaultMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    public static ResponseEntity<?> increaseImpressionSuccess() {
        return ResponseEntity.ok(RestMessage.INCREASE_IMPRESSION_SUCCESS.getValue());
    }
}
