package middle.shopservice.exception;

import lombok.Getter;
import middle.shopservice.controller.restResponse.ResponseMessage;

@Getter
public class ShopCustomException extends RuntimeException{
    private ResponseMessage responseMessage;

    public ShopCustomException(ResponseMessage responseMessage) {
        super(responseMessage.getValue());
        this.responseMessage = responseMessage;
    }
}
