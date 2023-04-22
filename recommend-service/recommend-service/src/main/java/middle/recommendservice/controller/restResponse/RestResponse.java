package middle.recommendservice.controller.restResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
}
