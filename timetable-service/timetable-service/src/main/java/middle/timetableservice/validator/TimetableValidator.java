package middle.timetableservice.validator;

import lombok.RequiredArgsConstructor;
import middle.timetableservice.authentication.Role;
import middle.timetableservice.controller.restResponse.ResponseMessage;
import middle.timetableservice.exception.TimetableCustomException;
import middle.timetableservice.repository.TimetableRepository;
import middle.timetableservice.utility.CommonUtils;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class TimetableValidator {

    private final TimetableRepository timetableRepository;

    public void validateTimetableNull(Long id) {
        Long foundId = timetableRepository.findOneByIdForValidation(id);

        if (CommonUtils.isNull(foundId)) {
            throw new TimetableCustomException(ResponseMessage.TIMETABLE_IS_NULL);
        }
    }

    public void validateAuth(String auth) {
        if (!Objects.equals(auth, Role.OWNER.getValue())) {
            throw new TimetableCustomException(ResponseMessage.AUTH_IS_NOT_OWNER);
        }
    }

    public void validateShop(Long foundShopId, Long shopId) {
        if (CommonUtils.isNull(foundShopId)) {
            throw new TimetableCustomException(ResponseMessage.SHOP_IS_NULL);
        }

        if (!foundShopId.equals(shopId)) {
            throw new TimetableCustomException(ResponseMessage.NOT_OWNER_OF_SHOP);
        }
    }
}
