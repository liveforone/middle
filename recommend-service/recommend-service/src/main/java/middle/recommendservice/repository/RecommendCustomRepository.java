package middle.recommendservice.repository;

import middle.recommendservice.domain.Recommend;

public interface RecommendCustomRepository {

    Long findOneIdByUsernameForValidation(String username);
    Recommend findOneByUsername(String username);
    Long countSizeOfRecommend();
    Recommend findOneByShopId(Long shopId);
    void deleteOneByShopId(Long shopId);
}
