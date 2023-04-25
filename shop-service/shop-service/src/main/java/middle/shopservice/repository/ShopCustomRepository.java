package middle.shopservice.repository;

import middle.shopservice.domain.Shop;
import middle.shopservice.dto.ShopResponse;

import java.util.List;

public interface ShopCustomRepository {

    Long findOneIdByIdForValidation(Long shopId);
    Long findOneIdByUsernameForValidation(String username);
    Long findOneByUsernameForProvide(String username);
    Shop findOneById(Long shopId);
    Shop findOneByUsername(String username);
    List<ShopResponse> findHomePage(Long recommendShopId, Long lastId);
    List<ShopResponse> searchShopPage(String city, String street, String shopName, Long recommendShopId, Long lastId);
    void deleteOneByUsername(String username);
}
