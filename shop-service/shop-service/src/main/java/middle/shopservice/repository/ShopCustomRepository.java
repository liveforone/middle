package middle.shopservice.repository;

import middle.shopservice.domain.Shop;
import middle.shopservice.dto.ShopResponse;

import java.util.List;

public interface ShopCustomRepository {

    Shop findOneById(Long shopId);
    List<ShopResponse> findHomePage(Long recommendShopId, Long lastId, int pageSize);
    List<ShopResponse> searchShopNamePage(String shopName, Long recommendShopId, Long lastId, int pageSize);
    List<ShopResponse> searchCityPage(String city, Long recommendShopId, Long lastId, int pageSize);
    List<ShopResponse> searchStreetPage(String street, Long recommendShopId, Long lastId, int pageSize);
    void deleteOneByUsername(String username);
}
