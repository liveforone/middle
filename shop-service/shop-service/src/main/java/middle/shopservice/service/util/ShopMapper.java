package middle.shopservice.service.util;

import middle.shopservice.domain.Shop;
import middle.shopservice.dto.ShopResponse;
import middle.shopservice.utility.CommonUtils;

public class ShopMapper {

    public static ShopResponse entityToDtoDetail(Shop shop) {
        if (CommonUtils.isNull(shop)) {
            return new ShopResponse();
        }

        return ShopResponse.builder()
                .id(shop.getId())
                .shopName(shop.getShopName())
                .tel(shop.getTel())
                .city(shop.getCity())
                .street(shop.getStreet())
                .detail(shop.getDetail())
                .build();
    }
}
