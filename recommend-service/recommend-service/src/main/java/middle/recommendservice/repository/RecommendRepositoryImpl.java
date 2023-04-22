package middle.recommendservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import middle.recommendservice.domain.QRecommend;
import middle.recommendservice.domain.Recommend;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RecommendRepositoryImpl implements RecommendCustomRepository {

    private final JPAQueryFactory queryFactory;
    QRecommend recommend = QRecommend.recommend;

    public Long findOneIdByUsernameForValidation(String username) {
        return queryFactory
                .select(recommend.id)
                .from(recommend)
                .where(recommend.username.eq(username))
                .fetchOne();
    }

    public Recommend findOneByUsername(String username) {
        return queryFactory
                .selectFrom(recommend)
                .where(recommend.username.eq(username))
                .fetchOne();
    }

    public Long countSizeOfRecommend() {
        return queryFactory
                .select(recommend.id.count())
                .from(recommend)
                .fetchOne();
    }

    public Recommend findOneByShopId(Long shopId) {
        return queryFactory
                .selectFrom(recommend)
                .where(recommend.shopId.eq(shopId))
                .fetchOne();
    }
}
