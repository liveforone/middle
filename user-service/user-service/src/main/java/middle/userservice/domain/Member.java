package middle.userservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import middle.userservice.dto.changeInfo.EditInfoRequest;
import middle.userservice.dto.signupAndLogin.MemberSignupRequest;
import middle.userservice.utility.PasswordUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Getter
@Builder
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

    @Builder
    public Member(Long id, String username, String email, String password, String realName, Role auth) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.realName = realName;
        this.auth = auth;
    }


    //==Domain Logic Space==//

    public void signup(MemberSignupRequest request) {
        final String ADMIN = "admin@maybeAllHere.com";
        if (Objects.equals(request.getEmail(), ADMIN)) {
            request.setAuth(Role.ADMIN);
        } else {
            request.setAuth(Role.MEMBER);
        }
        request.setPassword(PasswordUtils.encodePassword(request.getPassword()));

        buildingMember(request);
    }

    public void signupSeller(MemberSignupRequest request) {
        request.setPassword(PasswordUtils.encodePassword(request.getPassword()));
        request.setAuth(Role.OWNER);

        buildingMember(request);
    }

    private void buildingMember(MemberSignupRequest request) {
        this.id = request.getId();
        this.username = createUsername();
        this.email = request.getEmail();
        this.password = request.getPassword();
        this.realName = request.getRealName();
        this.auth = request.getAuth();
    }

    private String createUsername() {
        return UUID.randomUUID() + RandomStringUtils.random(MemberConstant.RANDOM_STRING_LENGTH);
    }

    public void updateMemberInfo(EditInfoRequest request) {
        this.email = request.getEmail();
        this.password = PasswordUtils.encodePassword(request.getPassword());
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
