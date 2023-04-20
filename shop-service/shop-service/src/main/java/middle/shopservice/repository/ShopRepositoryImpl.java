package middle.shopservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import middle.shopservice.domain.QShop;
import middle.shopservice.domain.Shop;
import org.springframework.stereotype.Repository;

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

    public void deleteOneByUsername(String username) {
        queryFactory.delete(shop)
                .where(shop.username.eq(username))
                .execute();
    }
}
