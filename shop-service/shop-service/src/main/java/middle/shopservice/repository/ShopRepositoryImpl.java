package middle.shopservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import middle.shopservice.domain.QShop;
import middle.shopservice.domain.Shop;
import middle.shopservice.dto.ShopRepositoryUtil;
import middle.shopservice.dto.ShopResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopCustomRepository {

    private final JPAQueryFactory queryFactory;
    QShop shop = QShop.shop;

    public Shop findOneById(Long shopId) {
        return queryFactory.selectFrom(shop)
                .where(shop.id.eq(shopId))
                .fetchOne();
    }

    public List<ShopResponse> findFirstHomePage(Long recommendShopId, Long lastId, int pageSize) {
        ShopResponse recommendShop = queryFactory
                .select(ShopRepositoryUtil.shopResponseConstructor())
                .where(shop.id.eq(recommendShopId))
                .fetchOne();

        List<ShopResponse> shopPageList = queryFactory
                .select(ShopRepositoryUtil.shopResponseConstructor())
                .where(ShopRepositoryUtil.ltShopId(lastId))
                .orderBy(shop.id.desc())
                .limit(pageSize)
                .fetch();

        shopPageList.add(ShopRepositoryUtil.ZERO_INDEX, recommendShop);

        return shopPageList;
    }

    public List<ShopResponse> findHomePage(Long lastId, int pageSize) {
        return queryFactory
                .select(ShopRepositoryUtil.shopResponseConstructor())
                .where(ShopRepositoryUtil.ltShopId(lastId))
                .orderBy(shop.id.desc())
                .limit(pageSize)
                .fetch();
    }

    public List<ShopResponse> searchShopNameFirstPage(String shopName, Long recommendShopId, Long lastId, int pageSize) {
        ShopResponse recommendShop = queryFactory
                .select(ShopRepositoryUtil.shopResponseConstructor())
                .where(shop.id.eq(recommendShopId))
                .fetchOne();

        List<ShopResponse> shopPageList = queryFactory
                .select(ShopRepositoryUtil.shopResponseConstructor())
                .where(
                        shop.shopName.startsWith(shopName),
                        ShopRepositoryUtil.ltShopId(lastId)
                )
                .orderBy(shop.id.desc())
                .limit(pageSize)
                .fetch();

        shopPageList.add(ShopRepositoryUtil.ZERO_INDEX, recommendShop);

        return shopPageList;
    }

    public List<ShopResponse> searchShopNamePage(String shopName, Long lastId, int pageSize) {
        return queryFactory
                .select(ShopRepositoryUtil.shopResponseConstructor())
                .where(
                        shop.shopName.startsWith(shopName),
                        ShopRepositoryUtil.ltShopId(lastId)
                )
                .orderBy(shop.id.desc())
                .limit(pageSize)
                .fetch();
    }

    public List<ShopResponse> searchCityFirstPage(String city, Long recommendShopId, Long lastId, int pageSize) {
        return null;
    }

    public List<ShopResponse> searchCityPage(String city, Long lastId, int pageSize) {
        return null;
    }

    public List<ShopResponse> searchStreetFirstPage(String street, Long recommendShopId, Long lastId, int pageSize) {
        return null;
    }

    public List<ShopResponse> searchStreetPage(String street, Long lastId, int pageSize) {
        return null;
    }

    public void deleteOneByUsername(String username) {
        queryFactory.delete(shop)
                .where(shop.username.eq(username))
                .execute();
    }
}
