package middle.timetableservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import middle.timetableservice.domain.QTimetable;
import middle.timetableservice.domain.Timetable;
import middle.timetableservice.dto.TimetableResponse;
import middle.timetableservice.repository.util.TimetableRepositoryUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimetableRepositoryImpl implements TimetableCustomRepository{

    private final JPAQueryFactory queryFactory;
    QTimetable timetable = QTimetable.timetable;

    public List<TimetableResponse> findTimetablesByShopId(Long shopId, Long lastId) {
        return queryFactory
                .select(TimetableRepositoryUtil.timetableDtoConstructor())
                .from(timetable)
                .where(
                        timetable.shopId.eq(shopId),
                        TimetableRepositoryUtil.ltTimetableId(lastId)
                )
                .orderBy(timetable.id.desc())
                .limit(TimetableRepositoryUtil.PAGE_SIZE)
                .fetch();
    }

    public Timetable findOneById(Long id) {
        return queryFactory
                .selectFrom(timetable)
                .where(timetable.id.eq(id))
                .fetchOne();
    }
}
