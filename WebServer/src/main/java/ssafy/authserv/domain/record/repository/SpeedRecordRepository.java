package ssafy.authserv.domain.record.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ssafy.authserv.domain.record.entity.SpeedRecord;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpeedRecordRepository extends JpaRepository<SpeedRecord, Long> {
    /**
     * Map별로 탑 10 기록을 페이지네이션하여 반환합니다.
     * @param mapNum : 맵 번호
     * @param pageable :  pagination과 정렬을 위한 정보
     * @return : (해당 페이지의) 탑 10 기록
     */
    @Query("SELECT s FROM SpeedRecord s WHERE s.map = :mapNum ORDER BY s.time ASC")
    Page<SpeedRecord> findTopRecordsByMap(@Param("mapNum") int mapNum, Pageable pageable);

    Boolean existsSpeedRecordByMemberId(UUID memberId);

    Optional<SpeedRecord> findByMemberIdAndMap(UUID memberId, int map);
}