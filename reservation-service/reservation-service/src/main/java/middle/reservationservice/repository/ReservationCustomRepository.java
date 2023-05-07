package middle.reservationservice.repository;

import middle.reservationservice.domain.Reservation;
import middle.reservationservice.dto.ReservationResponse;

import java.util.List;

public interface ReservationCustomRepository {
    Reservation findOneById(Long id);
    List<ReservationResponse> findPageByUsername(String username, Long lastId);
}
