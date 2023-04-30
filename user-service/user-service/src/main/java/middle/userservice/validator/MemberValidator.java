package middle.userservice.validator;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import middle.userservice.controller.constant.ControllerLog;
import middle.userservice.controller.restResponse.ResponseMessage;
import middle.userservice.domain.Member;
import middle.userservice.domain.Role;
import middle.userservice.exception.BindingCustomException;
import middle.userservice.exception.MemberCustomException;
import middle.userservice.kafka.UserProducer;
import middle.userservice.repository.MemberRepository;
import middle.userservice.utility.CommonUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class MemberValidator {

    private final MemberRepository memberRepository;
    private final UserProducer userProducer;

    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void validatePassword(String inputPassword, String username) {
        Member foundMember = memberRepository.findByUsername(username);
        String originalPassword = foundMember.getPassword();

        if (!passwordEncoder.matches(inputPassword, originalPassword)) {
            throw new MemberCustomException(ResponseMessage.NOT_MATCH_PASSWORD);
        }
    }

    public void validateBinding(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects
                    .requireNonNull(bindingResult.getFieldError())
                    .getDefaultMessage();
            throw new BindingCustomException(errorMessage);
        }
    }

    public void validateDuplicateEmail(String email) {
        Member member = memberRepository.findByEmail(email);

        if (!CommonUtils.isNull(member)) {
            throw new MemberCustomException(ResponseMessage.DUPLICATE_EMAIL);
        }
    }

    public void validateAuth(String auth, String username) {
        if (Objects.equals(auth, Role.OWNER.getValue())) {
            userProducer.removeShopBelongMember(username);
        }
    }

    public void validateAuth(Role auth) {
        if (!auth.equals(Role.ADMIN)) {
            log.error(ControllerLog.ADMIN_FAIL.getValue());
            throw new MemberCustomException(ResponseMessage.PROHIBITION);
        }
    }
}
