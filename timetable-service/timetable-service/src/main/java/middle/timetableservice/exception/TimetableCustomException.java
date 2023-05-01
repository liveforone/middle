package middle.timetableservice.exception;

import lombok.Getter;
import middle.timetableservice.controller.restResponse.ResponseMessage;

@Getter
public class TimetableCustomException extends RuntimeException{
    private ResponseMessage responseMessage;

    public TimetableCustomException(ResponseMessage responseMessage) {
        super(responseMessage.getValue());
        this.responseMessage = responseMessage;
    }
}
