package middle.timetableservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import middle.timetableservice.domain.QTimetable;
import middle.timetableservice.domain.Timetable;
import middle.timetableservice.dto.TimetableResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

import static middle.timetableservice.repository.util.TimetableRepoUtil.*;

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

    public Long findOneShopIdById(Long id) {
        return queryFactory.select(timetable.shopId)
                .from(timetable)
                .where(timetable.id.eq(id))
                .fetchOne();
    }

    public List<TimetableResponse> findTimetablesByShopId(Long shopId, Long lastId) {
        return queryFactory
                .select(timetableDtoConstructor())
                .from(timetable)
                .where(
                        timetable.shopId.eq(shopId),
                        ltTimetableId(lastId)
                )
                .orderBy(timetable.id.desc())
                .limit(PAGE_SIZE)
                .fetch();
    }

    public Timetable findOneById(Long id) {
        return queryFactory
                .selectFrom(timetable)
                .where(timetable.id.eq(id))
                .fetchOne();
    }

    public boolean minusRemaining(Long id) {
        long affectedRows = queryFactory
                .update(timetable)
                .set(
                        timetable.remaining,
                        timetable.remaining.add(MINUS_ONE)
                )
                .where(minusRemainingCondition(id))
                .execute();

        return affectedRows > ZERO_VALUE;
    }

    public boolean plusRemaining(Long id) {
        long affectedRows = queryFactory
                .update(timetable)
                .set(
                        timetable.remaining,
                        timetable.remaining.add(PLUS_ONE)
                )
                .where(plusRemainingCondition(id))
                .execute();

        return affectedRows > ZERO_VALUE;
    }

    public void deleteOneById(Long id) {
        queryFactory
                .delete(timetable)
                .where(timetable.id.eq(id))
                .execute();
    }

    public void deleteBulkByShopId(Long shopId) {
        queryFactory
                .delete(timetable)
                .where(timetable.shopId.eq(shopId))
                .execute();
    }

    public void restoreRemaining() {
        queryFactory
                .update(timetable)
                .set(timetable.remaining, timetable.basicRemaining)
                .execute();
    }
}
