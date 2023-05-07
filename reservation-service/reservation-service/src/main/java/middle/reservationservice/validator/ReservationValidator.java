package middle.reservationservice.validator;

import lombok.RequiredArgsConstructor;
import middle.reservationservice.controller.restResponse.ResponseMessage;
import middle.reservationservice.exception.ReservationCustomException;
import middle.reservationservice.repository.ReservationRepository;
import middle.reservationservice.utility.CommonUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationValidator {

    private final ReservationRepository reservationRepository;

    public void validateReservationNull(Long id) {
        Long foundId = reservationRepository.findIdForValidationById(id);

        if (CommonUtils.isNull(foundId)) {
            throw new ReservationCustomException(ResponseMessage.RESERVATION_IS_NULL);
        }
    }

    public void validateUsername(Long id, String username) {
        String foundUsername = reservationRepository.findUsernameForValidationById(id);

        if (!foundUsername.equals(username)) {
            throw new ReservationCustomException(ResponseMessage.USERNAME_NOT_MATCH);
        }
    }

    public void validateReserveTimetable(boolean bool) {
        if (!bool) {
            throw new ReservationCustomException(ResponseMessage.RESERVE_FAIL);
        }
    }


}
