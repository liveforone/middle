package middle.shopservice.validator;

import lombok.RequiredArgsConstructor;
import middle.shopservice.authentication.Role;
import middle.shopservice.repository.ShopRepository;
import middle.shopservice.utility.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ShopValidator {

    private final ShopRepository shopRepository;

    public boolean isNull(Long shopId) {
        Long foundId = shopRepository.findOneIdForValidation(shopId);

        return CommonUtils.isNull(foundId);
    }

    public boolean isNotOwner(String auth) {
        return !Objects.equals(auth, Role.OWNER.getValue());
    }
}
