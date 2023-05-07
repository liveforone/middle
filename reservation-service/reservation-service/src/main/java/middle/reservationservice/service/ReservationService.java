package middle.reservationservice.service;

import lombok.RequiredArgsConstructor;
import middle.reservationservice.dto.ReservationResponse;
import middle.reservationservice.repository.ReservationRepository;
import middle.reservationservice.service.util.ReservationMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final ReservationRepository reservationRepository;

    public ReservationResponse getReservationById(Long id) {
        return ReservationMapper.entityToDto(reservationRepository.findOneById(id));
    }
}
