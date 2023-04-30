package middle.recommendservice.validator;

import lombok.RequiredArgsConstructor;
import middle.recommendservice.controller.restResponse.ResponseMessage;
import middle.recommendservice.exception.BindingCustomException;
import middle.recommendservice.exception.RecommendCustomException;
import middle.recommendservice.repository.RecommendRepository;
import middle.recommendservice.utility.CommonUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RecommendValidator {

    private final RecommendRepository recommendRepository;

    public void validateBinding(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMessage = Objects
                    .requireNonNull(bindingResult.getFieldError())
                    .getDefaultMessage();
            throw new BindingCustomException(errorMessage);
        }
    }

    public void validateRecommendNull(String username) {
        Long foundId = recommendRepository.findOneIdByUsernameForValidation(username);

        if (CommonUtils.isNull(foundId)) {
            throw new RecommendCustomException(ResponseMessage.RECOMMEND_IS_NULL);
        }
    }

    public void validateShopNull(Long shopId) {
        if (CommonUtils.isNull(shopId)) {
            throw new RecommendCustomException(ResponseMessage.SHOP_IS_NULL);
        }
    }

    public void validateDuplicateRecommend(String username) {
        Long foundId = recommendRepository.findOneIdByUsernameForValidation(username);

        if (!CommonUtils.isNull(foundId)) {
            throw new RecommendCustomException(ResponseMessage.DUPLICATE_RECOMMEND);
        }
    }
}
