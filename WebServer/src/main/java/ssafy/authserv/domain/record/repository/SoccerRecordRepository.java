package ssafy.authserv.domain.record.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssafy.authserv.domain.record.entity.SoccerRecord;

import java.util.List;
import java.util.UUID;

@Repository
public interface SoccerRecordRepository extends JpaRepository<SoccerRecord, Long> {
    SoccerRecord findByMemberId(UUID memberId);
}
