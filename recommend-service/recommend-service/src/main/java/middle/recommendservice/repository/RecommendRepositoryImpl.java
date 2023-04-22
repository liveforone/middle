package middle.recommendservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import middle.recommendservice.domain.QRecommend;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecommendRepositoryImpl implements RecommendCustomRepository {

    private final JPAQueryFactory queryFactory;
    QRecommend recommend = QRecommend.recommend;

    public Long findOneIdByUsernameForValidation(String username) {
        return queryFactory.select(recommend.id)
                .from(recommend)
                .where(recommend.username.eq(username))
                .fetchOne();
    }
}
