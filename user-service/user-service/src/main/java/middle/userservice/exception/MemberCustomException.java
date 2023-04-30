package middle.userservice.exception;

import lombok.Getter;
import middle.userservice.controller.restResponse.ResponseMessage;

@Getter
public class MemberCustomException extends RuntimeException{
    private ResponseMessage responseMessage;

    public MemberCustomException(ResponseMessage responseMessage) {
        super(responseMessage.getValue());
        this.responseMessage = responseMessage;
    }
}
