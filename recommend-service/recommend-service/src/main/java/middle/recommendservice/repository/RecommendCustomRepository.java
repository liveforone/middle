package middle.recommendservice.repository;

import middle.recommendservice.domain.Recommend;

public interface RecommendCustomRepository {

    Long findOneIdByUsernameForValidation(String username);
    Recommend findOneByUsername(String username);
}
