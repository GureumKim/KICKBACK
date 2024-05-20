package ssafy.authserv.domain.record.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ssafy.authserv.domain.record.entity.SoccerRecord;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SoccerRecordRepository extends JpaRepository<SoccerRecord, Long> {
    @Query("SELECT sr FROM SoccerRecord sr WHERE sr.member.id = :memberId")
    Optional<SoccerRecord> findByMemberId(@Param("memberId") UUID memberId);

    /**
     * SoccerRecord의 wins 필드 값을 기준으로 정렬된 soccer 페이지를 가져옵니다.
     * @param pageable abstracts pagination and sorting information
     * @return Page of users
     */
    @Query("SELECT sr FROM SoccerRecord sr ORDER BY sr.wins DESC")
    Page<SoccerRecord> findSoccerRecordsByWins(Pageable pageable);

//    @Query("SELECT sr FROM SoccerRecord sr ORDER BY sr.scores DESC, sr.gd DESC")
    @Query("SELECT sr FROM SoccerRecord sr ORDER BY sr.scores DESC")
    List<SoccerRecord> getRankings();
}
