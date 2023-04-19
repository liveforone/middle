package middle.userservice.service;

import jakarta.persistence.EntityManager;
import middle.userservice.dto.changeInfo.ChangeEmailRequest;
import middle.userservice.dto.signupAndLogin.MemberSignupRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    String createMember(String email, String password, String realName) {
        MemberSignupRequest memberSignupRequest = new MemberSignupRequest();
        memberSignupRequest.setEmail(email);
        memberSignupRequest.setPassword(password);
        memberSignupRequest.setRealName(realName);
        return memberService.signup(memberSignupRequest);
    }

    @Test
    @Transactional
    void signupOwnerTest() {
        //given
        String email = "owner11@gmail.com";
        String password = "1111";
        String realName = "test_seller";
        MemberSignupRequest memberSignupRequest = new MemberSignupRequest();
        memberSignupRequest.setEmail(email);
        memberSignupRequest.setPassword(password);
        memberSignupRequest.setRealName(realName);

        //when
        String username = memberService.signupOwner(memberSignupRequest);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(memberService.getMemberByUsername(username).getRealName())
                .isEqualTo(realName);
    }

    @Test
    @Transactional
    void updateEmailTest() {
        //given
        String email = "abcd1234@gmail.com";
        String password = "1234";
        String realName = "test_member";
        String username = createMember(email, password, realName);
        em.flush();
        em.clear();

        //when
        String updatedEmail = "zzzz1111@gmail.com";
        ChangeEmailRequest request = new ChangeEmailRequest();
        request.setEmail(updatedEmail);
        memberService.updateEmail(request, username);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(memberService.getMemberByUsername(username).getEmail())
                .isEqualTo(updatedEmail);

    }
}