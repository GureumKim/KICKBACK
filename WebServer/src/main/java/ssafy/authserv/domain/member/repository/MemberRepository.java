package ssafy.authserv.domain.member.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ssafy.authserv.domain.member.entity.Member;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);

    // findById 메소드는 Optional<User>를 반환합니다.
    // 이는 결과값이 없을 경우 NullPointerException을 방지하기 위해 사용됩니다.
    Optional<Member> findByEmail(String email);

    /**
     * SoccerRecord의 wins 필드 값을 기준으로
     * 정렬된 사용자 페이지를 가져옵니다.
     * @param pageable abstracts pagination and sorting information
     * @return Page of users
     */
    @Query("SELECT m FROM Member m JOIN FETCH m.soccerRecord r ORDER BY r.wins DESC")
    Page<Member> findMembersBySoccerWins(Pageable pageable);
}
