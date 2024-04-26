package ssafy.authserv.domain.record.service;

import org.springframework.scheduling.annotation.Async;
import ssafy.authserv.domain.member.entity.Member;

import java.util.UUID;

public interface RecordService {
    @Async("threadPoolTaskExecutor")
    void saveSoccerRecord(Member member);

    @Async("threadPoolTaskExecutor")
    void testSaveSpeed(UUID memberId, int map, float time);
}
