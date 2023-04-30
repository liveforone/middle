package middle.recommendservice.controller.restResponse;

import org.springframework.http.ResponseEntity;

public class RestResponse {

    public static ResponseEntity<?> createRecommendSuccess() {
        return ResponseEntity
                .status(ResponseMessage.CREATE_RECOMMEND_SUCCESS.getStatus())
                .body(ResponseMessage.CREATE_RECOMMEND_SUCCESS.getValue());
    }

    public static ResponseEntity<?> increaseImpressionSuccess() {
        return ResponseEntity.ok(ResponseMessage.INCREASE_IMPRESSION_SUCCESS.getValue());
    }
}
