package middle.reservationservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import middle.reservationservice.domain.QReservation;
import middle.reservationservice.domain.Reservation;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryImpl implements ReservationCustomRepository {

    private final JPAQueryFactory queryFactory;
    QReservation reservation = QReservation.reservation;

    public Reservation findOneById(Long id) {
        return queryFactory
                .selectFrom(reservation)
                .where(reservation.id.eq(id))
                .fetchOne();
    }
}
