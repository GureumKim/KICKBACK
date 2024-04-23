package ssafy.authserv.domain.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssafy.authserv.domain.member.entity.Member;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    boolean existsByEmail(String email);

    // findById 메소드는 Optional<User>를 반환합니다.
    // 이는 결과값이 없을 경우 NullPointerException을 방지하기 위해 사용됩니다.
    Optional<Member> findByEmail(String email);
}
