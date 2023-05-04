package middle.shopservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import middle.shopservice.domain.QShop;
import middle.shopservice.domain.Shop;
import middle.shopservice.dto.ShopResponse;
import middle.shopservice.utility.CommonUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static middle.shopservice.repository.util.ShopRepositoryUtil.*;

@Repository
@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopCustomRepository {

    private final JPAQueryFactory queryFactory;
    QShop shop = QShop.shop;

    public Long findOneIdByIdForValidation(Long shopId) {
        return queryFactory.select(shop.id)
                .from(shop)
                .where(shop.id.eq(shopId))
                .fetchOne();
    }

    public Long findOneIdByUsernameForValidation(String username) {
        return queryFactory.select(shop.id)
                .from(shop)
                .where(shop.username.eq(username))
                .fetchOne();
    }

    public Long findOneByUsernameForProvide(String username) {
        return queryFactory.select(shop.id)
                .from(shop)
                .where(shop.username.eq(username))
                .fetchOne();
    }

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

    private ShopResponse createRecommendShop(Long recommendShopId) {
        return queryFactory
                .select(shopDtoConstructor())
                .from(shop)
                .where(shop.id.eq(recommendShopId))
                .fetchOne();
    }

    public List<ShopResponse> findHomePage(Long recommendShopId, Long lastId) {
        List<ShopResponse> shopPageList = queryFactory
                .select(shopDtoConstructor())
                .from(shop)
                .where(ltShopId(lastId))
                .orderBy(shop.id.desc())
                .limit(PAGE_SIZE)
                .fetch();

        if (CommonUtils.isNull(recommendShopId)) {
            return shopPageList;
        }

        ShopResponse recommendShop = createRecommendShop(recommendShopId);

        if (!CommonUtils.isNull(recommendShop)) {
            shopPageList.add(ZERO_INDEX, recommendShop);
        }

        return shopPageList;
    }

    public List<ShopResponse> searchShopPage(
            String city, String street,
            String shopName, Long recommendShopId, Long lastId
    ) {
        List<ShopResponse> shopPageList = queryFactory
                .select(shopDtoConstructor())
                .from(shop)
                .where(
                        searchCity(city),
                        searchStreet(street),
                        searchName(shopName),
                        ltShopId(lastId)
                )
                .orderBy(shop.id.desc())
                .limit(PAGE_SIZE)
                .fetch();

        if (CommonUtils.isNull(recommendShopId)) {
            return shopPageList;
        }

        ShopResponse recommendShop = createRecommendShop(recommendShopId);

        if (!CommonUtils.isNull(recommendShop)) {
            shopPageList.add(ZERO_INDEX, recommendShop);
        }

        return shopPageList;
    }

    public void deleteOneByUsername(String username) {
        queryFactory.delete(shop)
                .where(shop.username.eq(username))
                .execute();
    }
}
