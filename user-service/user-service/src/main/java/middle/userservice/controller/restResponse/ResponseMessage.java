package middle.userservice.controller.restResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseMessage {

    HOME(200, "home"),
    DUPLICATE_EMAIL(400, "중복되는 이메일이 있어 회원가입이 불가능합니다."),
    SIGNUP_PAGE(200, "가입할 이메일과 비밀번호 그리고 실명을 입력해주세요"),
    SIGNUP_SUCCESS(201, "반갑습니다. 회원가입에 성공하셨습니다."),
    LOGIN_PAGE(200, "이메일과 비밀번호를 입력하세요."),
    LOGIN_SUCCESS(200, "로그인에 성공하였습니다."),
    LOGIN_FAIL(400, "이메일 혹은 비밀번호가 다릅니다.\n다시 시도하세요."),
    CHANGE_EMAIL_SUCCESS(200, "이메일이 변경되었습니다."),
    NOT_MATCH_PASSWORD(400, "비밀번호가 다릅니다. 다시 입력해주세요."),
    CHANGE_PASSWORD_SUCCESS(200, "비밀번호가 변경되었습니다."),
    WITHDRAW_SUCCESS(200, "그동안 서비스를 이용해주셔서 감사합니다."),
    PROHIBITION(401, "접근 권한이 없습니다.");

    private final int status;
    private final String value;
}
