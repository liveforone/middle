package middle.userservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import middle.userservice.domain.util.MemberConstant;
import middle.userservice.dto.signupAndLogin.MemberSignupRequest;
import middle.userservice.domain.util.PasswordUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(nullable = false)
    private String realName;

    @Enumerated(value = EnumType.STRING)
    private Role auth;

    private Member(String username, String email, String password, String realName, Role auth) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.realName = realName;
        this.auth = auth;
    }

    //==Domain Logic Space==//

    public static Member create(MemberSignupRequest request) {
        final String ADMIN = "admin@maybeAllHere.com";

        if (Objects.equals(request.getEmail(), ADMIN)) {
            request.setAuth(Role.ADMIN);
        } else {
            request.setAuth(Role.MEMBER);
        }
        request.setPassword(PasswordUtils.encodePassword(request.getPassword()));

        return new Member(
                createUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getRealName(),
                request.getAuth()
        );
    }

    public static Member createOwner(MemberSignupRequest request) {
        request.setPassword(PasswordUtils.encodePassword(request.getPassword()));
        request.setAuth(Role.OWNER);

        return new Member(
                createUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getRealName(),
                request.getAuth()
        );
    }

    private static String createUsername() {
        return UUID.randomUUID() + RandomStringUtils.randomAlphabetic(MemberConstant.RANDOM_STRING_LENGTH);
    }

    public void updateEmail(String newEmail) {
        this.email = newEmail;
    }

    public void updatePassword(String password) {
        this.password = PasswordUtils.encodePassword(password);
    }

    //==End Domain Logic Space==//


    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = new ArrayList<>();
        authList.add(new SimpleGrantedAuthority(auth.getValue()));
        return authList;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
