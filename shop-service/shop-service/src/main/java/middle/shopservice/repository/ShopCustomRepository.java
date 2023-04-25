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
    List<ShopResponse> findHomePage(Long recommendShopId, Long lastId, int pageSize);
    List<ShopResponse> searchShopNamePage(String shopName, Long recommendShopId, Long lastId, int pageSize, String order);
    List<ShopResponse> searchCityPage(String city, Long recommendShopId, Long lastId, int pageSize, String order);
    List<ShopResponse> searchStreetPage(String street, Long recommendShopId, Long lastId, int pageSize, String order);
    void deleteOneByUsername(String username);
}
