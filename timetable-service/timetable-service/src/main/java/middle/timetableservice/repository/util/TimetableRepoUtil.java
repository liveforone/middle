package middle.timetableservice.repository.util;

import com.querydsl.core.types.ConstructorExpression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import middle.timetableservice.domain.QTimetable;
import middle.timetableservice.dto.TimetableResponse;

public class TimetableRepoUtil {

    public static final int PAGE_SIZE = 15;
    public static final long MINUS_ONE = -1;
    public static final long ZERO_VALUE = 0;
    private static final QTimetable timetable;

    static {
        timetable = QTimetable.timetable;
    }

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

    public static BooleanExpression minusRemainingCondition(Long id) {
        return timetable.id.eq(id)
                .and(timetable.remaining.gt(ZERO_VALUE));
    }
}
