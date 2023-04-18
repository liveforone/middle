package middle.userservice.exception;

import middle.userservice.controller.restResponse.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MemberControllerAdvice {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<?> loginFailHandle() {
        return RestResponse.loginFail();
    }
}
