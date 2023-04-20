package middle.shopservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopRequest {

    private Long id;

    @NotBlank(message = "상호명은 반드시 기입해야합니다.")
    private String shopName;

    @NotBlank(message = "전화번호는 반드시 기입해야합니다.")
    private String tel;

    @NotBlank(message = "시재지는 반드시 기입해야합니다.")
    private String city;

    @NotBlank(message = "도로명 주소는 반드시 기입해야합니다.")
    private String street;

    @NotBlank(message = "상세 주소는 반드시 기입해야합니다.")
    private String detail;
}
