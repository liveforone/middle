package middle.reservationservice.repository;

import middle.reservationservice.domain.Reservation;
import middle.reservationservice.dto.ReservationResponse;

import java.time.LocalDate;
import java.util.List;

public interface ReservationCustomRepository {

    Long findIdForValidationById(Long id);
    String findUsernameForValidationById(Long id);
    LocalDate findCreatedDateForValidationById(Long id);
    Long findTimetableIdForCancel(Long id);
    Reservation findOneById(Long id);
    List<ReservationResponse> findPageByUsername(String username, Long lastId);
    List<ReservationResponse> findPageByShopId(Long shopId, Long lastId);
}
