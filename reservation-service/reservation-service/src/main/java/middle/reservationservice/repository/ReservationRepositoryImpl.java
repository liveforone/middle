package middle.reservationservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl {

    private final JPAQueryFactory queryFactory;
}
