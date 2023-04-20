package middle.shopservice.validator;

import lombok.RequiredArgsConstructor;
import middle.shopservice.repository.ShopRepository;
import middle.shopservice.utility.CommonUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ShopValidator {

    private final ShopRepository shopRepository;

    public boolean isNull(Long shopId) {
        Long foundId = shopRepository.findOneIdForValidation(shopId);

        return CommonUtils.isNull(foundId);
    }
}
