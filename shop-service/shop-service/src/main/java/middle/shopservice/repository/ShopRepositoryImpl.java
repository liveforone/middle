package middle.shopservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ShopRepositoryImpl implements ShopCustomRepository {

    private final JPAQueryFactory queryFactory;
}
