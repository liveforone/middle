package middle.reservationservice.service.util;

import middle.reservationservice.domain.Reservation;
import middle.reservationservice.dto.ReservationResponse;

public class ReservationMapper {
    public static ReservationResponse entityToDto(Reservation reservation) {
        return ReservationResponse.builder()
                .id(reservation.getId())
                .timetableId(reservation.getTimetableId())
                .reservationState(reservation.getReservationState())
                .createdDate(reservation.getCreatedDate())
                .build();
    }
}
