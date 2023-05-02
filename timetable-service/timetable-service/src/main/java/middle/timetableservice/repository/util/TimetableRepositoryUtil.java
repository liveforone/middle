package middle.timetableservice.repository.util;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import middle.timetableservice.domain.QTimetable;
import middle.timetableservice.dto.TimetableResponse;

public class TimetableRepositoryUtil {

    public static final int PAGE_SIZE = 15;
    static QTimetable timetable = QTimetable.timetable;

    public static BooleanExpression ltTimetableId(Long lastId) {
        if (lastId == 0) {
            return null;
        }

        return timetable.id.lt(lastId);
    }

    public static ConstructorExpression<TimetableResponse> timetableDtoConstructor() {
        return Projections.constructor(TimetableResponse.class,
                timetable.id,
                timetable.reservationHour,
                timetable.reservationMinute,
                timetable.remaining);
    }
}
