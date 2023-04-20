package middle.shopservice.repository;

import middle.shopservice.domain.Shop;
import middle.shopservice.dto.ShopResponse;

import java.util.List;

public interface ShopCustomRepository {

    Shop findOneById(Long shopId);
    List<ShopResponse> findFirstHomePage(Long recommendShopId, Long lastId, int pageSize);
    List<ShopResponse> findHomePage(Long lastId, int pageSize);
    List<ShopResponse> searchShopNameFirstPage(String shopName, Long recommendShopId, Long lastId, int pageSize);
    List<ShopResponse> searchShopNamePage(String shopName, Long lastId, int pageSize);
    List<ShopResponse> searchCityFirstPage(String city, Long recommendShopId, Long lastId, int pageSize);
    List<ShopResponse> searchCityPage(String city, Long lastId, int pageSize);
    List<ShopResponse> searchStreetFirstPage(String street, Long recommendShopId, Long lastId, int pageSize);
    List<ShopResponse> searchStreetPage(String street, Long lastId, int pageSize);
    void deleteOneByUsername(String username);
}
