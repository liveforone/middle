package middle.shopservice.validator;

import lombok.RequiredArgsConstructor;
import middle.shopservice.authentication.Role;
import middle.shopservice.controller.restResponse.ResponseMessage;
import middle.shopservice.exception.BindingCustomException;
import middle.shopservice.exception.ShopCustomException;
import middle.shopservice.repository.ShopRepository;
import middle.shopservice.utility.CommonUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ShopValidator {

    private final ShopRepository shopRepository;

    public void validateShopNull(Long shopId) {
        Long foundId = shopRepository.findOneIdByIdForValidation(shopId);

        if (CommonUtils.isNull(foundId)) {
            throw new ShopCustomException(ResponseMessage.SHOP_IS_NULL);
        }
    }

    public void validateShopNull(String username) {
        Long foundId = shopRepository.findOneIdByUsernameForValidation(username);

        if (CommonUtils.isNull(foundId)) {
            throw new ShopCustomException(ResponseMessage.SHOP_IS_NULL);
        }
    }

    public void validateAuth(String auth) {
        if (!Objects.equals(auth, Role.OWNER.getValue())) {
            throw new ShopCustomException(ResponseMessage.AUTH_IS_NOT_OWNER);
        }
    }

    public void validateBinding(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects
                    .requireNonNull(bindingResult.getFieldError())
                    .getDefaultMessage();
            throw new BindingCustomException(errorMessage);
        }
    }

    public void validateDuplicateOwner(String username) {
        Long foundId = shopRepository.findOneIdByUsernameForValidation(username);

        if (!CommonUtils.isNull(foundId)) {
            throw new ShopCustomException(ResponseMessage.DUPLICATE_OWNER);
        }
    }
}
