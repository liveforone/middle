package middle.userservice.validator;

import lombok.RequiredArgsConstructor;
import middle.userservice.domain.Member;
import middle.userservice.repository.MemberRepository;
import middle.userservice.utility.CommonUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberValidator {

    private final MemberRepository memberRepository;

    static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public boolean isNotMatchingPassword(String inputPassword, String username) {
        Member foundMember = memberRepository.findByUsername(username);
        String originalPassword = foundMember.getPassword();

        return !passwordEncoder.matches(inputPassword, originalPassword);
    }

    public boolean isDuplicateEmail(String email) {
        Member member = memberRepository.findByEmail(email);

        return !CommonUtils.isNull(member);
    }
}
