package middle.recommendservice.validator;

import lombok.RequiredArgsConstructor;
import middle.recommendservice.repository.RecommendRepository;
import middle.recommendservice.utility.CommonUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RecommendValidator {

    private final RecommendRepository recommendRepository;

    public boolean isNotExistRecommend(String username) {
        Long foundId = recommendRepository.findOneIdByUsernameForValidation(username);

        return CommonUtils.isNull(foundId);
    }

    public boolean isDuplicateRecommend(String username) {
        Long foundId = recommendRepository.findOneIdByUsernameForValidation(username);

        return !CommonUtils.isNull(foundId);
    }
}
