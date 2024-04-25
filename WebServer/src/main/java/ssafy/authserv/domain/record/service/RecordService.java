package ssafy.authserv.domain.record.service;

import org.springframework.scheduling.annotation.Async;
import ssafy.authserv.domain.member.entity.Member;

public interface RecordService {
    @Async("threadPoolTaskExecutor")
    void saveSoccerRecord(Member member);
}
