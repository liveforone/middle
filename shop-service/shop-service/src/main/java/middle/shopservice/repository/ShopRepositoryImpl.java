package middle.shopservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import middle.shopservice.domain.QShop;
import middle.shopservice.domain.Shop;
import middle.shopservice.dto.ShopResponse;
import middle.shopservice.repository.util.ShopRepositoryUtil;
import middle.shopservice.utility.CommonUtils;
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

    public Shop findOneByUsername(String username) {
        return queryFactory.selectFrom(shop)
                .where(shop.username.eq(username))
                .fetchOne();
    }

    public List<ShopResponse> findHomePage(Long recommendShopId, Long lastId, int pageSize) {
        List<ShopResponse> shopPageList = queryFactory
                .select(ShopRepositoryUtil.shopResponseConstructor())
                .from(shop)
                .where(ShopRepositoryUtil.ltShopId(lastId))
                .orderBy(shop.id.desc())
                .limit(pageSize)
                .fetch();

        if (CommonUtils.isNull(recommendShopId)) {
            return shopPageList;
        }

        ShopResponse recommendShop = queryFactory
                .select(ShopRepositoryUtil.shopResponseConstructor())
                .from(shop)
                .where(shop.id.eq(recommendShopId))
                .fetchOne();

        if (!CommonUtils.isNull(recommendShop)) {
            shopPageList.add(ShopRepositoryUtil.ZERO_INDEX, recommendShop);
        }

        return shopPageList;
    }

    public List<ShopResponse> searchShopNamePage(String shopName, Long recommendShopId, Long lastId, int pageSize) {
        List<ShopResponse> shopPageList = queryFactory
                .select(ShopRepositoryUtil.shopResponseConstructor())
                .from(shop)
                .where(
                        shop.shopName.startsWith(shopName),
                        ShopRepositoryUtil.ltShopId(lastId)
                )
                .orderBy(shop.id.desc())
                .limit(pageSize)
                .fetch();

        if (CommonUtils.isNull(recommendShopId)) {
            return shopPageList;
        }

        ShopResponse recommendShop = queryFactory
                .select(ShopRepositoryUtil.shopResponseConstructor())
                .from(shop)
                .where(shop.id.eq(recommendShopId))
                .fetchOne();

        if (!CommonUtils.isNull(recommendShop)) {
            shopPageList.add(ShopRepositoryUtil.ZERO_INDEX, recommendShop);
        }

        return shopPageList;
    }

    public List<ShopResponse> searchCityPage(String city, Long recommendShopId, Long lastId, int pageSize) {
        List<ShopResponse> shopPageList = queryFactory
                .select(ShopRepositoryUtil.shopResponseConstructor())
                .from(shop)
                .where(
                        shop.city.startsWith(city),
                        ShopRepositoryUtil.ltShopId(lastId)
                )
                .orderBy(shop.id.desc())
                .limit(pageSize)
                .fetch();

        if (CommonUtils.isNull(recommendShopId)) {
            return shopPageList;
        }

        ShopResponse recommendShop = queryFactory
                .select(ShopRepositoryUtil.shopResponseConstructor())
                .from(shop)
                .where(shop.id.eq(recommendShopId))
                .fetchOne();

        if (!CommonUtils.isNull(recommendShop)) {
            shopPageList.add(ShopRepositoryUtil.ZERO_INDEX, recommendShop);
        }

        return shopPageList;
    }

    public List<ShopResponse> searchStreetPage(String street, Long recommendShopId, Long lastId, int pageSize) {
        List<ShopResponse> shopPageList = queryFactory
                .select(ShopRepositoryUtil.shopResponseConstructor())
                .from(shop)
                .where(
                        shop.street.startsWith(street),
                        ShopRepositoryUtil.ltShopId(lastId)
                )
                .orderBy(shop.id.desc())
                .limit(pageSize)
                .fetch();

        if (CommonUtils.isNull(recommendShopId)) {
            return shopPageList;
        }

        ShopResponse recommendShop = queryFactory
                .select(ShopRepositoryUtil.shopResponseConstructor())
                .from(shop)
                .where(shop.id.eq(recommendShopId))
                .fetchOne();

        if (!CommonUtils.isNull(recommendShop)) {
            shopPageList.add(ShopRepositoryUtil.ZERO_INDEX, recommendShop);
        }

        return shopPageList;
    }

    public void deleteOneByUsername(String username) {
        queryFactory.delete(shop)
                .where(shop.username.eq(username))
                .execute();
    }
}
