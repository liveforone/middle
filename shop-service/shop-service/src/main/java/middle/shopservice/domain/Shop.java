package middle.shopservice.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import middle.shopservice.dto.ShopAddressRequest;
import middle.shopservice.dto.ShopRequest;

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

    @Builder
    public Shop(Long id, String username, String shopName, String tel, String city, String street, String detail, long good, long bad) {
        this.id = id;
        this.username = username;
        this.shopName = shopName;
        this.tel = tel;
        this.city = city;
        this.street = street;
        this.detail = detail;
        this.good = good;
        this.bad = bad;
    }

    public void create(ShopRequest shopRequest, String username) {
        this.username = username;
        this.shopName = shopRequest.getShopName();
        this.tel = shopRequest.getTel();
        this.city = shopRequest.getCity();
        this.street = shopRequest.getStreet();
        this.detail = shopRequest.getDetail();
    }

    public void updateShopName(String shopName) {
        this.shopName = shopName;
    }

    public void updateTel(String tel) {
        this.tel = tel;
    }

    public void updateAddress(ShopAddressRequest request) {
        this.city = request.getCity();
        this.street = request.getStreet();
        this.detail = request.getDetail();
    }

    public void increaseGood() {
        this.good += 1;
    }

    public void increaseBad() {
        this.bad += 1;
    }
}
