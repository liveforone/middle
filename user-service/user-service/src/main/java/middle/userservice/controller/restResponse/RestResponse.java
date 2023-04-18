package middle.userservice.controller.restResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.Objects;

public class RestResponse {

    public static ResponseEntity<?> home() {
        return ResponseEntity.ok(ResponseMessage.HOME.getValue());
    }

    public static ResponseEntity<?> signupPage() {
        return ResponseEntity.ok(ResponseMessage.SIGNUP_PAGE.getValue());
    }

    public static ResponseEntity<?> validError(BindingResult bindingResult) {
        String errorMessage = Objects
                .requireNonNull(bindingResult.getFieldError())
                .getDefaultMessage();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorMessage);
    }

    public static ResponseEntity<?> duplicateEmail() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.DUPLICATE_EMAIL.getValue());
    }

    public static ResponseEntity<?> signupSuccess() {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseMessage.SIGNUP_SUCCESS.getValue());
    }

    public static ResponseEntity<?> loginPage() {
        return ResponseEntity.ok(ResponseMessage.LOGIN_PAGE.getValue());
    }

    public static ResponseEntity<?> loginSuccess() {
        return ResponseEntity.ok(ResponseMessage.LOGIN_SUCCESS.getValue());
    }

    public static ResponseEntity<?> loginFail() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.LOGIN_FAIL.getValue());
    }

    public static ResponseEntity<?> changeEmailSuccess() {
        return ResponseEntity.ok(ResponseMessage.CHANGE_EMAIL_SUCCESS.getValue());
    }

    public static ResponseEntity<?> notMatchPassword() {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ResponseMessage.NOT_MATCH_PASSWORD.getValue());
    }

    public static ResponseEntity<?> changePasswordSuccess() {
        return ResponseEntity.ok(ResponseMessage.CHANGE_PASSWORD_SUCCESS.getValue());
    }

    public static ResponseEntity<?> withdrawSuccess() {
        return ResponseEntity.ok(ResponseMessage.WITHDRAW_SUCCESS.getValue());
    }

    public static ResponseEntity<?> prohibition() {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ResponseMessage.PROHIBITION.getValue());
    }
}
