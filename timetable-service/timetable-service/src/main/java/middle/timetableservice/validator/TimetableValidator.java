package middle.timetableservice.validator;

import lombok.RequiredArgsConstructor;
import middle.timetableservice.controller.restResponse.ResponseMessage;
import middle.timetableservice.exception.TimetableCustomException;
import middle.timetableservice.repository.TimetableRepository;
import middle.timetableservice.utility.CommonUtils;
import org.springframework.stereotype.Component;

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
}
