package middle.userservice.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import middle.userservice.domain.Member;
import middle.userservice.domain.QMember;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    QMember member = QMember.member;

    public Member findByUsername(String username) {
        return queryFactory.selectFrom(member)
                .where(member.username.eq(username))
                .fetchOne();
    }

    public Member findByEmail(String email) {
        return queryFactory.selectFrom(member)
                .where(member.email.eq(email))
                .fetchOne();
    }

    public void deleteByUsername(String username) {
        queryFactory.delete(member)
                .where(member.username.eq(username))
                .execute();
    }
}
