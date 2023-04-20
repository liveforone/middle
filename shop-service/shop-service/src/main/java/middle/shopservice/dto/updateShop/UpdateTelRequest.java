package middle.shopservice.dto.updateShop;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTelRequest {

    @NotBlank(message = "전화번흐는 반드시 기입해야합니다.")
    private String tel;
}
