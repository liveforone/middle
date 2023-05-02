package middle.timetableservice.repository;

import middle.timetableservice.domain.Timetable;
import middle.timetableservice.dto.TimetableResponse;

import java.util.List;

public interface TimetableCustomRepository {

    Long findOneByIdForValidation(Long id);
    List<TimetableResponse> findTimetablesByShopId(Long shopId, Long lastId);
    Timetable findOneById(Long id);
    boolean minusRemaining(Long id);
    void deleteOneById(Long id);
    void deleteBulkByShopId(Long shopId);
    void restoreRemaining();
}
