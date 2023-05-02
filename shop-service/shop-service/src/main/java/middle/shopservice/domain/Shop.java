package middle.shopservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import middle.shopservice.dto.updateShop.UpdateAddressRequest;
import middle.shopservice.dto.ShopRequest;
import middle.shopservice.dto.updateShop.UpdateNameRequest;
import middle.shopservice.dto.updateShop.UpdateTelRequest;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, updatable = false)
    private String username;

    @Column(nullable = false)
    private String shopName;

    @Column(nullable = false)
    private String tel;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String street;

    @Column(nullable = false)
    private String detail;

    private long good;
    private long bad;

    private Shop(String username, String shopName, String tel, String city, String street, String detail) {
        this.username = username;
        this.shopName = shopName;
        this.tel = tel;
        this.city = city;
        this.street = street;
        this.detail = detail;
    }

    public static Shop create(ShopRequest shopRequest, String username) {
        return new Shop(
                username,
                shopRequest.getShopName(),
                shopRequest.getTel(),
                shopRequest.getCity(),
                shopRequest.getStreet(),
                shopRequest.getDetail()
        );
    }

    public void updateShopName(UpdateNameRequest updateNameRequest) {
        this.shopName = updateNameRequest.getShopName();
    }

    public void updateTel(UpdateTelRequest updateTelRequest) {
        this.tel = updateTelRequest.getTel();
    }

    public void updateAddress(UpdateAddressRequest request) {
        this.city = request.getCity();
        this.street = request.getStreet();
        this.detail = request.getDetail();
    }

    public void increaseGood() {
        final long BASIC_INCREASE = 1;
        this.good += BASIC_INCREASE;
    }

    public void increaseBad() {
        final long BASIC_DECREASE = 1;
        this.bad += BASIC_DECREASE;
    }
}
