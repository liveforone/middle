package middle.shopservice.service;

import jakarta.persistence.EntityManager;
import middle.shopservice.dto.ShopRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class ShopServiceTest {

    @Autowired
    ShopService shopService;

    @Autowired
    EntityManager em;

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
    void updateShopName() {
    }

    @Test
    void updateTel() {
    }

    @Test
    void updateAddress() {
    }
}