package middle.userservice.dto.changeInfo;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangeEmailRequest {

    @Email(message = "이메일 형식을 지켜 작성해주세요.")
    private String email;
}
