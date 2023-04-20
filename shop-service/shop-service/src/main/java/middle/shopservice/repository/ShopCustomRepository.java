package middle.shopservice.repository;

import middle.shopservice.domain.Shop;

public interface ShopCustomRepository {

    Shop findOneById(Long shopId);

//    void deleteOneByUsername(String username);
}
