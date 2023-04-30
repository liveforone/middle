package middle.shopservice.controller.restResponse;

import org.springframework.http.ResponseEntity;

public class RestResponse {

    public static ResponseEntity<?> createShopSuccess() {
        return ResponseEntity
                .status(ResponseMessage.CREATE_SHOP_SUCCESS.getStatus())
                .body(ResponseMessage.CREATE_SHOP_SUCCESS.getValue());
    }

    public static ResponseEntity<?> updateNameSuccess() {
        return ResponseEntity.ok(ResponseMessage.UPDATE_NAME_SUCCESS.getValue());
    }

    public static ResponseEntity<?> updateTelSuccess() {
        return ResponseEntity.ok(ResponseMessage.UPDATE_TEL_SUCCESS.getValue());
    }

    public static ResponseEntity<?> updateAddressSuccess() {
        return ResponseEntity.ok(ResponseMessage.UPDATE_ADDRESS_SUCCESS.getValue());
    }
}
