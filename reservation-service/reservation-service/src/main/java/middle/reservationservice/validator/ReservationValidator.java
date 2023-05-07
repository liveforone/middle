package middle.reservationservice.validator;

import lombok.RequiredArgsConstructor;
import middle.reservationservice.controller.restResponse.ResponseMessage;
import middle.reservationservice.exception.ReservationCustomException;
import middle.reservationservice.repository.ReservationRepository;
import middle.reservationservice.utility.CommonUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

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

    public void validateShop(Long shopId) {
        if (CommonUtils.isNull(shopId)) {
            throw new ReservationCustomException(ResponseMessage.CANT_FIND_SHOP);
        }
    }

    public void validateCancelDate(Long id) {
        LocalDate createdDate = reservationRepository.findCreatedDateForValidationById(id);
        LocalDate nowDate = LocalDate.now();

        if (!nowDate.equals(createdDate)) {
            throw new ReservationCustomException(ResponseMessage.CANCEL_FAIL);
        }
    }
}
