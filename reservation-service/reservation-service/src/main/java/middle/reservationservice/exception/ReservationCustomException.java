package middle.reservationservice.exception;

import lombok.Getter;
import middle.reservationservice.controller.restResponse.ResponseMessage;

@Getter
public class ReservationCustomException extends RuntimeException{
    private ResponseMessage responseMessage;

    public ReservationCustomException(ResponseMessage responseMessage) {
        super(responseMessage.getValue());
        this.responseMessage = responseMessage;
    }
}
