package middle.recommendservice.exception;

import lombok.Getter;
import middle.recommendservice.controller.restResponse.ResponseMessage;

@Getter
public class RecommendCustomException extends RuntimeException{
    private ResponseMessage responseMessage;

    public RecommendCustomException(ResponseMessage responseMessage) {
        super(responseMessage.getValue());
        this.responseMessage = responseMessage;
    }
}
