package middle.timetableservice.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

    ADMIN("ROLE_ADMIN"),
    MEMBER("ROLE_MEMBER"),
    OWNER("ROLE_OWNER");

    private String value;
}
