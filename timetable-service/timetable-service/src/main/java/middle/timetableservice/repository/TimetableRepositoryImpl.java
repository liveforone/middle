package middle.timetableservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import middle.timetableservice.domain.QTimetable;
import middle.timetableservice.domain.Timetable;
import middle.timetableservice.dto.TimetableResponse;
import middle.timetableservice.repository.util.TimetableRepoUtil;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TimetableRepositoryImpl implements TimetableCustomRepository{

    private final JPAQueryFactory queryFactory;
    QTimetable timetable = QTimetable.timetable;

    public Long findOneByIdForValidation(Long id) {
        return queryFactory
                .select(timetable.id)
                .from(timetable)
                .where(timetable.id.eq(id))
                .fetchOne();
    }

    public List<TimetableResponse> findTimetablesByShopId(Long shopId, Long lastId) {
        return queryFactory
                .select(TimetableRepoUtil.timetableDtoConstructor())
                .from(timetable)
                .where(
                        timetable.shopId.eq(shopId),
                        TimetableRepoUtil.ltTimetableId(lastId)
                )
                .orderBy(timetable.id.desc())
                .limit(TimetableRepoUtil.PAGE_SIZE)
                .fetch();
    }

    public Timetable findOneById(Long id) {
        return queryFactory
                .selectFrom(timetable)
                .where(timetable.id.eq(id))
                .fetchOne();
    }

    public boolean minusRemaining(Long id) {
        long updatedRemaining = queryFactory
                .update(timetable)
                .set(
                        timetable.remaining,
                        timetable.remaining.add(TimetableRepoUtil.MINUS_ONE)
                )
                .where(TimetableRepoUtil.minusRemainingCondition(id))
                .execute();

        return updatedRemaining > TimetableRepoUtil.ZERO_VALUE;
    }
}
