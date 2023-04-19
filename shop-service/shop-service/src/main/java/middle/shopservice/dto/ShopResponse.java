package middle.shopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopResponse {

    private String shopName;
    private String tel;
    private String city;
    private String street;
    private String detail;
    private long good;
    private long bad;
}
