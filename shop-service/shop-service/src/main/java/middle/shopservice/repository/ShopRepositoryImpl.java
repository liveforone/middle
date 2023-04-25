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
                .select(ShopRepositoryUtil.shopDtoConstructor())
                .from(shop)
                .where(shop.id.eq(recommendShopId))
                .fetchOne();
    }

    public List<ShopResponse> findHomePage(Long recommendShopId, Long lastId) {
        List<ShopResponse> shopPageList = queryFactory
                .select(ShopRepositoryUtil.shopDtoConstructor())
                .from(shop)
                .where(ShopRepositoryUtil.ltShopId(lastId))
                .orderBy(shop.id.desc())
                .limit(ShopRepositoryUtil.PAGE_SIZE)
                .fetch();

        if (CommonUtils.isNull(recommendShopId)) {
            return shopPageList;
        }

        ShopResponse recommendShop = createRecommendShop(recommendShopId);

        if (!CommonUtils.isNull(recommendShop)) {
            shopPageList.add(ShopRepositoryUtil.ZERO_INDEX, recommendShop);
        }

        return shopPageList;
    }

    public List<ShopResponse> searchShopPage(
            String city, String street,
            String shopName, Long recommendShopId, Long lastId
    ) {
        List<ShopResponse> shopPageList = queryFactory
                .select(ShopRepositoryUtil.shopDtoConstructor())
                .from(shop)
                .where(
                        ShopRepositoryUtil.searchCity(city),
                        ShopRepositoryUtil.searchStreet(street),
                        ShopRepositoryUtil.searchName(shopName),
                        ShopRepositoryUtil.ltShopId(lastId)
                )
                .orderBy(shop.id.desc())
                .limit(ShopRepositoryUtil.PAGE_SIZE)
                .fetch();

        if (CommonUtils.isNull(recommendShopId)) {
            return shopPageList;
        }

        ShopResponse recommendShop = createRecommendShop(recommendShopId);

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
