package ssafy.authserv.domain.record.service;

import org.springframework.scheduling.annotation.Async;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.record.dto.UpdateSoccerRecordRequest;

import java.util.UUID;

public interface RecordService {
    @Async("threadPoolTaskExecutor")
    void saveSoccerRecord(Member member);

    @Async("threadPoolTaskExecutor")
    void updateSoccerRecord(UUID memberId, UpdateSoccerRecordRequest request);

    @Async("threadPoolTaskExecutor")
    void updateSpeedRecord(UUID memberId, String mapName, String time);
}
