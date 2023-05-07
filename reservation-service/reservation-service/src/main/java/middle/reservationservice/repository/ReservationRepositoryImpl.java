package middle.reservationservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import middle.reservationservice.domain.QReservation;
import middle.reservationservice.domain.Reservation;
import middle.reservationservice.dto.ReservationResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

import static middle.reservationservice.repository.util.ReservationRepositoryUtil.*;

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

    public List<ReservationResponse> findPageByUsername(String username, Long lastId) {
        return queryFactory
                .select(reservationDtoConstructor())
                .from(reservation)
                .where(
                        reservation.username.eq(username),
                        ltReservationId(lastId)
                )
                .orderBy(reservation.id.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }

    public List<ReservationResponse> findPageByShopId(Long shopId, Long lastId) {
        return queryFactory
                .select(reservationDtoConstructor())
                .from(reservation)
                .where(
                        reservation.shopId.eq(shopId),
                        ltReservationId(lastId)
                )
                .orderBy(reservation.id.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }
}
