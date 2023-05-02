package middle.shopservice.service;

import jakarta.persistence.EntityManager;
import middle.shopservice.dto.ShopRequest;
import middle.shopservice.dto.updateShop.UpdateAddressRequest;
import middle.shopservice.dto.updateShop.UpdateNameRequest;
import middle.shopservice.dto.updateShop.UpdateTelRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ShopServiceTest {  //!!반드시 서비스에 async 어노테이션 주석처리하고 테스트 합니다!!

    @Autowired
    ShopService shopService;

    @Autowired
    EntityManager em;

    void createShop(String username, String shopName, String tel, String city, String street, String detail) {
        ShopRequest shopRequest = ShopRequest.builder()
                .shopName(shopName)
                .tel(tel)
                .city(city)
                .street(street)
                .detail(detail)
                .build();
        shopService.createShop(shopRequest, username);
    }

    @Test
    @Transactional
    void createShopTest() {
        //given
        String username = "2ojfesdafjljeoafjoewjfoajof";
        String shopName = "test1";
        String tel = "01000000000";
        String city = "seoul";
        String street = "jongro";
        String detail = "changshin building 54-1";
        ShopRequest shopRequest = ShopRequest.builder()
                .shopName(shopName)
                .tel(tel)
                .city(city)
                .street(street)
                .detail(detail)
                .build();

        //when
        shopService.createShop(shopRequest, username);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(shopService.getShopByUsername(username).getShopName())
                .isEqualTo(shopName);
    }

    @Test
    @Transactional
    void updateShopNameTest() {
        //given
        String username = "2ojfesdafjljeoafjoewjfoajof";
        String shopName = "test1";
        String tel = "01000000000";
        String city = "seoul";
        String street = "jongro";
        String detail = "changshin building 54-1";
        createShop(username, shopName, tel, city, street, detail);

        //when
        String updateShopName = "update_test1";
        UpdateNameRequest request = new UpdateNameRequest();
        request.setShopName(updateShopName);
        shopService.updateShopName(request, username);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(shopService.getShopByUsername(username).getShopName())
                .isEqualTo(updateShopName);
    }

    @Test
    @Transactional
    void updateTelTest() {
        //given
        String username = "2ojfesdafjljeoafjoewjfoajof";
        String shopName = "test1";
        String tel = "01000000000";
        String city = "seoul";
        String street = "jongro";
        String detail = "changshin building 54-1";
        createShop(username, shopName, tel, city, street, detail);

        //when
        String updatedTel = "01012345678";
        UpdateTelRequest request = new UpdateTelRequest();
        request.setTel(updatedTel);
        shopService.updateTel(request, username);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(shopService.getShopByUsername(username).getTel())
                .isEqualTo(updatedTel);
    }

    @Test
    @Transactional
    void updateAddressTest() {
        //given
        String username = "2ojfesdafjljeoafjoewjfoajof";
        String shopName = "test1";
        String tel = "01000000000";
        String city = "seoul";
        String street = "jongro";
        String detail = "changshin building 54-1";
        createShop(username, shopName, tel, city, street, detail);

        //when
        String updatedDetail = "bomun building 11-11";
        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setCity(city);
        request.setStreet(street);
        request.setDetail(updatedDetail);
        shopService.updateAddress(request, username);
        em.flush();
        em.clear();

        //then
        Assertions
                .assertThat(shopService.getShopByUsername(username).getDetail())
                .isEqualTo(updatedDetail);
    }
}