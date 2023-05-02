package middle.timetableservice.repository;

import middle.timetableservice.domain.Timetable;
import middle.timetableservice.dto.TimetableResponse;

import java.util.List;

public interface TimetableCustomRepository {

    List<TimetableResponse> findTimetablesByShopId(Long shopId, Long lastId);
    Timetable findOneById(Long id);
}
