package middle.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.userservice.authentication.AuthenticationInfo;
import middle.userservice.controller.constant.ControllerLog;
import middle.userservice.controller.constant.MemberUrl;
import middle.userservice.controller.restResponse.RestResponse;
import middle.userservice.domain.Role;
import middle.userservice.dto.changeInfo.ChangeEmailRequest;
import middle.userservice.dto.changeInfo.ChangePasswordRequest;
import middle.userservice.dto.response.MemberResponse;
import middle.userservice.dto.signupAndLogin.MemberLoginRequest;
import middle.userservice.dto.signupAndLogin.MemberSignupRequest;
import middle.userservice.jwt.TokenInfo;
import middle.userservice.jwt.constant.JwtConstant;
import middle.userservice.kafka.UserProducer;
import middle.userservice.service.MemberService;
import middle.userservice.validator.MemberValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MemberValidator memberValidator;
    private final AuthenticationInfo authenticationInfo;
    private final UserProducer userProducer;

    @GetMapping(MemberUrl.HOME)
    public ResponseEntity<?> home() {
        return RestResponse.home();
    }

    @GetMapping(MemberUrl.SIGNUP)
    public ResponseEntity<?> signupPage() {
        return RestResponse.signupPage();
    }

    @PostMapping(MemberUrl.SIGNUP)
    public ResponseEntity<?> signup(
            @RequestBody @Valid MemberSignupRequest memberSignupRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        String email = memberSignupRequest.getEmail();
        if (memberValidator.isDuplicateEmail(email)) {
            return RestResponse.duplicateEmail();
        }

        memberService.signup(memberSignupRequest);
        log.info(ControllerLog.SIGNUP_SUCCESS.getValue());

        return RestResponse.signupSuccess();
    }

    @GetMapping(MemberUrl.OWNER_SIGNUP)
    public ResponseEntity<?> signupOwnerPage() {
        return RestResponse.signupPage();
    }

    @PostMapping(MemberUrl.OWNER_SIGNUP)
    public ResponseEntity<?> signupOwner(
            @RequestBody @Valid MemberSignupRequest memberSignupRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        String email = memberSignupRequest.getEmail();
        if (memberValidator.isDuplicateEmail(email)) {
            return RestResponse.duplicateEmail();
        }

        memberService.signupOwner(memberSignupRequest);
        log.info(ControllerLog.SIGNUP_SUCCESS.getValue());

        return RestResponse.signupSuccess();
    }

    @GetMapping(MemberUrl.LOGIN)
    public ResponseEntity<?> loginPage() {
        return RestResponse.loginPage();
    }

    @PostMapping(MemberUrl.LOGIN)
    public ResponseEntity<?> login(
            @RequestBody @Valid MemberLoginRequest memberLoginRequest,
            BindingResult bindingResult,
            HttpServletResponse response
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        TokenInfo tokenInfo = memberService.login(memberLoginRequest);
        log.info(ControllerLog.LOGIN_SUCCESS.getValue());

        response.addHeader(JwtConstant.ACCESS_TOKEN, tokenInfo.getAccessToken());
        response.addHeader(JwtConstant.REFRESH_TOKEN, tokenInfo.getRefreshToken());
        return RestResponse.loginSuccess();
    }

    @GetMapping(MemberUrl.MY_PAGE)
    public ResponseEntity<MemberResponse> myPage(HttpServletRequest request) {
        String username = authenticationInfo.getUsername(request);
        MemberResponse member = memberService.getMemberByUsername(username);

        return ResponseEntity.ok(member);
    }

    @PatchMapping(MemberUrl.CHANGE_EMAIL)
    public ResponseEntity<?> changeEmail(
            @RequestBody @Valid ChangeEmailRequest changeEmailRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        if (memberValidator.isDuplicateEmail(changeEmailRequest.getEmail())) {
            return RestResponse.duplicateEmail();
        }

        String username = authenticationInfo.getUsername(request);
        memberService.updateEmail(changeEmailRequest, username);
        log.info(ControllerLog.CHANGE_EMAIL_SUCCESS.getValue());

        return RestResponse.changeEmailSuccess();
    }

    @PatchMapping(MemberUrl.CHANGE_PASSWORD)
    public ResponseEntity<?> changePassword(
            @RequestBody @Valid ChangePasswordRequest changePasswordRequest,
            BindingResult bindingResult,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            return RestResponse.validError(bindingResult);
        }

        String inputPw = changePasswordRequest.getOldPassword();
        String username = authenticationInfo.getUsername(request);
        if (memberValidator.isNotMatchingPassword(inputPw, username)) {
            return RestResponse.notMatchPassword();
        }

        String requestPw = changePasswordRequest.getNewPassword();
        memberService.updatePassword(requestPw, username);
        log.info(ControllerLog.CHANGE_PASSWORD_SUCCESS.getValue());

        return RestResponse.changePasswordSuccess();
    }

    @DeleteMapping(MemberUrl.WITHDRAW)
    public ResponseEntity<?> withdraw(
            @RequestBody String password,
            HttpServletRequest request
    ) {
        String username = authenticationInfo.getUsername(request);
        if (memberValidator.isNotMatchingPassword(password, username)) {
            return RestResponse.notMatchPassword();
        }

        String auth = authenticationInfo.getAuth(request);
        if (memberValidator.isOwner(auth)) {
            userProducer.removeShopBelongMember(username);
        }

        memberService.withdrawByUsername(username);
        log.info(ControllerLog.WITHDRAW_SUCCESS.getValue() + username);

        return RestResponse.withdrawSuccess();
    }

    @GetMapping(MemberUrl.ADMIN)
    public ResponseEntity<?> adminPage(HttpServletRequest request) {
        String username = authenticationInfo.getUsername(request);
        MemberResponse foundMember = memberService.getMemberByUsername(username);

        if (!memberValidator.isOwner(foundMember.getAuth())) {
            log.error(ControllerLog.ADMIN_FAIL.getValue());
            return RestResponse.prohibition();
        }

        List<MemberResponse> allMembers = memberService.getAllMemberForAdmin();
        log.info(ControllerLog.ADMIN_SUCCESS.getValue());

        return ResponseEntity.ok(allMembers);
    }

    @GetMapping(MemberUrl.PROHIBITION)
    public ResponseEntity<?> prohibition() {
        return RestResponse.prohibition();
    }
}
