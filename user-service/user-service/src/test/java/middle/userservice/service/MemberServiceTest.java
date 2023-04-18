package middle.userservice.service;

import jakarta.persistence.EntityManager;
import middle.userservice.dto.signupAndLogin.MemberSignupRequest;
import middle.userservice.repository.MemberRepository;
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
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    @Transactional
    void signupOwner() {
        //given
        String email = "owner11@gmail.com";
        String password = "1111";
        String realName = "test_seller";
        MemberSignupRequest memberSignupRequest = new MemberSignupRequest();
        memberSignupRequest.setEmail(email);
        memberSignupRequest.setPassword(password);
        memberSignupRequest.setRealName(realName);

        //when
        memberService.signupOwner(memberSignupRequest);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(memberRepository.findByEmail(email).getRealName())
                .isEqualTo(realName);
    }
}