package middle.recommendservice.repository;

import middle.recommendservice.cache.CacheConstant;
import middle.recommendservice.domain.Recommend;
import org.springframework.cache.annotation.Cacheable;

public interface RecommendCustomRepository {

    Long findOneIdByUsernameForValidation(String username);
    Recommend findOneByUsername(String username);
    @Cacheable(cacheNames = CacheConstant.COUNT_RECOMMEND_NAME, value = CacheConstant.COUNT_RECOMMEND)
    Long countSizeOfRecommend();
    Recommend findOneByShopId(Long shopId);
}
