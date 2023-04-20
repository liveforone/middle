package middle.shopservice.dto.updateShop;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateAddressRequest {

    @NotBlank(message = "시재지는 반드시 기입해야합니다.")
    private String city;

    @NotBlank(message = "도로명 주소는 반드시 기입해야합니다.")
    private String street;

    @NotBlank(message = "상세 주소는 반드시 기입해야합니다.")
    private String detail;
}
