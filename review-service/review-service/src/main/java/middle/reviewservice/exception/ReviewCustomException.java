package middle.reviewservice.exception;

import lombok.Getter;
import middle.reviewservice.controller.restResponse.ResponseMessage;

@Getter
public class ReviewCustomException extends RuntimeException{
    private ResponseMessage responseMessage;

    public ReviewCustomException(ResponseMessage responseMessage) {
        super(responseMessage.getValue());
        this.responseMessage = responseMessage;
    }
}
