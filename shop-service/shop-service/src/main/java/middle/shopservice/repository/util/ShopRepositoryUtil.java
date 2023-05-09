package middle.shopservice.repository.util;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import middle.shopservice.domain.QShop;
import middle.shopservice.dto.ShopResponse;
import middle.shopservice.utility.CommonUtils;

public class ShopRepositoryUtil {

    public static final int ZERO_INDEX = 0;
    public static final int PAGE_SIZE = 10;
    private static final QShop shop;

    static {
        shop = QShop.shop;
    }

    public static BooleanExpression ltShopId(Long lastId) {
        if (lastId == 0) {
            return null;
        }

        return shop.id.lt(lastId);
    }

    public static ConstructorExpression<ShopResponse> shopDtoConstructor() {
        return Projections.constructor(ShopResponse.class,
                shop.id,
                shop.shopName,
                shop.tel,
                shop.city,
                shop.street,
                shop.detail);
    }

    public static BooleanExpression searchName(String shopName) {
        if (CommonUtils.isNull(shopName)) {
            return null;
        }

        return shop.shopName.startsWith(shopName);
    }

    public static BooleanExpression searchCity(String city) {
        if (CommonUtils.isNull(city)) {
            return null;
        }

        return shop.city.startsWith(city);
    }

    public static BooleanExpression searchStreet(String street) {
        if (CommonUtils.isNull(street)) {
            return null;
        }

        return shop.street.startsWith(street);
    }
}
