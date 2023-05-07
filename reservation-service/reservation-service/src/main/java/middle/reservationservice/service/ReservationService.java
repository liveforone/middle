package middle.reservationservice.service;

import lombok.RequiredArgsConstructor;
import middle.reservationservice.domain.Reservation;
import middle.reservationservice.dto.ReservationResponse;
import middle.reservationservice.repository.ReservationRepository;
import middle.reservationservice.service.util.ReservationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationResponse getReservationById(Long id) {
        return ReservationMapper.entityToDto(reservationRepository.findOneById(id));
    }

    public List<ReservationResponse> getReservationsByUsername(String username, Long lastId) {
        return reservationRepository.findPageByUsername(username, lastId);
    }

    public List<ReservationResponse> getReservationsByShopId(Long shopId, Long lastId) {
        return reservationRepository.findPageByShopId(shopId, lastId);
    }

    @Transactional
    public void reserve(Long timetableId, String username, Long shopId) {
        Reservation reservation = Reservation.create(timetableId, username, shopId);
        reservationRepository.save(reservation);
    }
}
