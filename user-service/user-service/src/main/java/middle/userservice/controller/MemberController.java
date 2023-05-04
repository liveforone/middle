package middle.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.userservice.authentication.AuthenticationInfo;
import middle.userservice.controller.constant.ControllerLog;
import middle.userservice.controller.restResponse.RestResponse;
import middle.userservice.dto.changeInfo.ChangeEmailRequest;
import middle.userservice.dto.changeInfo.ChangePasswordRequest;
import middle.userservice.dto.response.MemberResponse;
import middle.userservice.dto.signupAndLogin.MemberLoginRequest;
import middle.userservice.dto.signupAndLogin.MemberSignupRequest;
import middle.userservice.jwt.TokenInfo;
import middle.userservice.jwt.constant.JwtConstant;
import middle.userservice.service.MemberService;
import middle.userservice.validator.MemberValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static middle.userservice.controller.constant.MemberUrl.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MemberValidator memberValidator;
    private final AuthenticationInfo authenticationInfo;

    @GetMapping(HOME)
    public ResponseEntity<?> home() {
        return RestResponse.home();
    }

    @GetMapping(SIGNUP)
    public ResponseEntity<?> signupPage() {
        return RestResponse.signupPage();
    }

    @PostMapping(SIGNUP)
    public ResponseEntity<?> signup(
            @RequestBody @Valid MemberSignupRequest memberSignupRequest,
            BindingResult bindingResult
    ) {
        memberValidator.validateBinding(bindingResult);

        String email = memberSignupRequest.getEmail();
        memberValidator.validateDuplicateEmail(email);

        memberService.signup(memberSignupRequest);
        log.info(ControllerLog.SIGNUP_SUCCESS.getValue());

        return RestResponse.signupSuccess();
    }

    @GetMapping(OWNER_SIGNUP)
    public ResponseEntity<?> signupOwnerPage() {
        return RestResponse.signupPage();
    }

    @PostMapping(OWNER_SIGNUP)
    public ResponseEntity<?> signupOwner(
            @RequestBody @Valid MemberSignupRequest memberSignupRequest,
            BindingResult bindingResult
    ) {
        memberValidator.validateBinding(bindingResult);

        String email = memberSignupRequest.getEmail();
        memberValidator.validateDuplicateEmail(email);

        memberService.signupOwner(memberSignupRequest);
        log.info(ControllerLog.SIGNUP_SUCCESS.getValue());

        return RestResponse.signupSuccess();
    }

    @GetMapping(LOGIN)
    public ResponseEntity<?> loginPage() {
        return RestResponse.loginPage();
    }

    @PostMapping(LOGIN)
    public ResponseEntity<?> login(
            @RequestBody @Valid MemberLoginRequest memberLoginRequest,
            BindingResult bindingResult,
            HttpServletResponse response
    ) {
        memberValidator.validateBinding(bindingResult);

        TokenInfo tokenInfo = memberService.login(memberLoginRequest);
        log.info(ControllerLog.LOGIN_SUCCESS.getValue());

        response.addHeader(JwtConstant.ACCESS_TOKEN, tokenInfo.getAccessToken());
        response.addHeader(JwtConstant.REFRESH_TOKEN, tokenInfo.getRefreshToken());
        return RestResponse.loginSuccess();
    }

    @GetMapping(MY_PAGE)
    public ResponseEntity<MemberResponse> myPage(HttpServletRequest request) {
        String username = authenticationInfo.getUsername(request);
        MemberResponse member = memberService.getMemberByUsername(username);

        return ResponseEntity.ok(member);
    }

    @PatchMapping(CHANGE_EMAIL)
    public ResponseEntity<?> changeEmail(
            @RequestBody @Valid ChangeEmailRequest changeEmailRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        memberValidator.validateBinding(bindingResult);
        memberValidator.validateDuplicateEmail(changeEmailRequest.getEmail());

        String username = authenticationInfo.getUsername(request);
        memberService.updateEmail(changeEmailRequest, username);
        log.info(ControllerLog.CHANGE_EMAIL_SUCCESS.getValue());

        return RestResponse.changeEmailSuccess();
    }

    @PatchMapping(CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        memberValidator.validateBinding(bindingResult);

        String inputPw = changePasswordRequest.getOldPassword();
        String username = authenticationInfo.getUsername(request);
        memberValidator.validatePassword(inputPw, username);

        String requestPw = changePasswordRequest.getNewPassword();
        memberService.updatePassword(requestPw, username);
        log.info(ControllerLog.CHANGE_PASSWORD_SUCCESS.getValue());

        return RestResponse.changePasswordSuccess();
    }

    @DeleteMapping(WITHDRAW)
    public ResponseEntity<?> withdraw(
            @RequestBody String password,
            HttpServletRequest request
    ) {
        String username = authenticationInfo.getUsername(request);
        memberValidator.validatePassword(password, username);

        String auth = authenticationInfo.getAuth(request);
        memberValidator.validateAuth(auth, username);

        memberService.withdrawByUsername(username);
        log.info(ControllerLog.WITHDRAW_SUCCESS.getValue() + username);

        return RestResponse.withdrawSuccess();
    }

    @GetMapping(ADMIN)
    public ResponseEntity<?> adminPage(HttpServletRequest request) {
        String username = authenticationInfo.getUsername(request);
        memberValidator.validateAdmin(username);

        List<MemberResponse> allMembers = memberService.getAllMemberForAdmin();
        log.info(ControllerLog.ADMIN_SUCCESS.getValue());

        return ResponseEntity.ok(allMembers);
    }

    @GetMapping(PROHIBITION)
    public ResponseEntity<?> prohibition() {
        return RestResponse.prohibition();
    }
}
