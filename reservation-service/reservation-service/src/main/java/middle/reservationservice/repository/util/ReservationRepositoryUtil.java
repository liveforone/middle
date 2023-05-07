package middle.reservationservice.repository.util;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import middle.reservationservice.domain.QReservation;
import middle.reservationservice.dto.ReservationResponse;

public class ReservationRepositoryUtil {
    private static final QReservation reservation;
    public static final int PAGE_SIZE = 15;

    static {
        reservation = QReservation.reservation;
    }

    public static ConstructorExpression<ReservationResponse> reservationDtoConstructor() {
        return Projections.constructor(ReservationResponse.class,
                reservation.id,
                reservation.timetableId,
                reservation.reservationState,
                reservation.createdDate
        );
    }

    public static BooleanExpression ltReservationId(Long lastId) {
        if (lastId == 0) {
            return null;
        }

        return reservation.id.lt(lastId);
    }
}
