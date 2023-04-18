package middle.userservice.service;

import lombok.RequiredArgsConstructor;
import middle.userservice.async.AsyncConstant;
import middle.userservice.domain.Member;
import middle.userservice.dto.changeInfo.ChangeEmailRequest;
import middle.userservice.dto.response.MemberResponse;
import middle.userservice.dto.signupAndLogin.MemberLoginRequest;
import middle.userservice.dto.signupAndLogin.MemberSignupRequest;
import middle.userservice.jwt.JwtTokenProvider;
import middle.userservice.jwt.TokenInfo;
import middle.userservice.repository.MemberRepository;
import middle.userservice.service.util.MemberMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    public MemberResponse getMemberByUsername(String username) {
        return MemberMapper.entityToDto(memberRepository.findByUsername(username));
    }

    public List<MemberResponse> getAllMemberForAdmin() {
        return MemberMapper.entityToDtoList(memberRepository.findAll());
    }


    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void signup(MemberSignupRequest memberSignupRequest) {
        Member member = Member.builder().build();
        member.signup(memberSignupRequest);

        memberRepository.save(member);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void signupOwner(MemberSignupRequest memberSignupRequest) {
        Member member = Member.builder().build();
        member.signupOwner(memberSignupRequest);

        memberRepository.save(member);
    }

    @Transactional
    public TokenInfo login(MemberLoginRequest memberLoginRequest) {
        String email = memberLoginRequest.getEmail();
        String password = memberLoginRequest.getPassword();

        Member member = memberRepository.findByEmail(email);
        String username = member.getUsername();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManagerBuilder
                .getObject()
                .authenticate(authenticationToken);

        return jwtTokenProvider
                .generateToken(authentication);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void updateEmail(ChangeEmailRequest changeEmailRequest, String username) {
        String newEmail = changeEmailRequest.getEmail();
        Member member = memberRepository.findByUsername(username);
        member.updateEmail(newEmail);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void updatePassword(String inputPassword, String email) {
        Member member = memberRepository.findByEmail(email);
        member.updatePassword(inputPassword);
    }

    @Transactional
    @Async(AsyncConstant.commandAsync)
    public void withdrawByUsername(String username) {
        memberRepository.deleteByUsername(username);
    }
}
