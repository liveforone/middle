package middle.shopservice.repository.util;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import middle.shopservice.domain.QShop;
import middle.shopservice.dto.ShopResponse;

public class ShopRepositoryUtil {

    public static final int ZERO_INDEX = 0;
    static QShop shop = QShop.shop;

    public static BooleanExpression ltShopId(Long lastId) {
        if (lastId == 0) {
            return null;
        }

        return shop.id.lt(lastId);
    }

    public static ConstructorExpression<ShopResponse> shopResponseConstructor() {
        return Projections.constructor(ShopResponse.class,
                shop.id,
                shop.shopName,
                shop.tel,
                shop.city,
                shop.street,
                shop.detail,
                shop.good,
                shop.bad);
    }
}
