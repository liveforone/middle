package middle.reservationservice.repository;

import middle.reservationservice.domain.Reservation;

public interface ReservationCustomRepository {
    Reservation findOneById(Long id);
}
