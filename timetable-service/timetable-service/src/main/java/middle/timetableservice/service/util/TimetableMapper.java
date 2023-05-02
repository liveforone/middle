package middle.timetableservice.service.util;

import middle.timetableservice.domain.Timetable;
import middle.timetableservice.dto.TimetableResponse;
import middle.timetableservice.utility.CommonUtils;

public class TimetableMapper {

    public static TimetableResponse entityToDto(Timetable timetable) {
        if (CommonUtils.isNull(timetable)) {
            return new TimetableResponse();
        }

        return TimetableResponse.builder()
                .id(timetable.getId())
                .reservationHour(timetable.getReservationHour())
                .reservationMinute(timetable.getReservationMinute())
                .remaining(timetable.getRemaining())
                .build();
    }
}
