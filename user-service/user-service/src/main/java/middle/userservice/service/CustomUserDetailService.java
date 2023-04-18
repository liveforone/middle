package middle.userservice.service;

import lombok.RequiredArgsConstructor;
import middle.userservice.domain.Member;
import middle.userservice.domain.Role;
import middle.userservice.repository.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return createUserDetails(memberRepository.findByUsername(email));
    }

    private UserDetails createUserDetails(Member member) {
        if (member.getAuth() == Role.ADMIN) {
            String ADMIN_ROLE = "ADMIN";
            return User.builder()
                    .username(member.getUsername())
                    .password(member.getPassword())
                    .roles(ADMIN_ROLE)
                    .build();
        } else if(member.getAuth() == Role.OWNER) {
            String OWNER_ROLE = "OWNER";
            return User.builder()
                    .username(member.getUsername())
                    .password(member.getPassword())
                    .roles(OWNER_ROLE)
                    .build();
        } else {
            String MEMBER_ROLE = "MEMBER";
            return User.builder()
                    .username(member.getUsername())
                    .password(member.getPassword())
                    .roles(MEMBER_ROLE)
                    .build();
        }
    }
}
