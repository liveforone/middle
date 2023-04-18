package middle.userservice.repository;

import middle.userservice.domain.Member;

public interface MemberRepositoryCustom {

    Member findByUsername(String username);

    void deleteByUsername(String username);
}
