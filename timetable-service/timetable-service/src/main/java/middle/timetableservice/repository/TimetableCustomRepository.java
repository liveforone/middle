package middle.timetableservice.repository;

import middle.timetableservice.domain.Timetable;
import middle.timetableservice.dto.TimetableResponse;

import java.util.List;

public interface TimetableCustomRepository {

    Long findOneByIdForValidation(Long id);
    Long findOneShopIdById(Long id);
    List<TimetableResponse> findTimetablesByShopId(Long shopId, Long lastId);
    Timetable findOneById(Long id);
    boolean minusRemaining(Long id);
    boolean plusRemaining(Long id);
    void deleteOneById(Long id);
    void deleteBulkByShopId(Long shopId);
    void restoreRemaining();
}
