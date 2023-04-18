package middle.userservice.repository;

import middle.userservice.domain.Member;

public interface MemberRepositoryCustom {

    Member findByUsername(String username);

    Member findByEmail(String email);

    void deleteByUsername(String username);
}
