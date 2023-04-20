package middle.shopservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateNameRequest {

    @NotBlank(message = "상점 이름을 반드시 등록해주세요")
    private String shopName;
}
