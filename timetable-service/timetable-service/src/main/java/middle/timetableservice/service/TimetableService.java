package middle.timetableservice.service;

import lombok.RequiredArgsConstructor;
import middle.timetableservice.domain.Timetable;
import middle.timetableservice.dto.TimetableResponse;
import middle.timetableservice.repository.TimetableRepository;
import middle.timetableservice.service.util.TimetableMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TimetableService {

    private final TimetableRepository timetableRepository;

    public List<TimetableResponse> getTimetablesByShopId(Long shopId, Long lastId) {
        return timetableRepository.findTimetablesByShopId(shopId, lastId);
    }

    public TimetableResponse getTimetableById(Long id) {
        return TimetableMapper.entityToDto(timetableRepository.findOneById(id));
    }
}
