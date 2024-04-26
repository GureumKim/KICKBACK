package ssafy.authserv.domain.record.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.exception.MemberErrorCode;
import ssafy.authserv.domain.member.exception.MemberException;
import ssafy.authserv.domain.member.repository.MemberRepository;
import ssafy.authserv.domain.record.entity.SoccerRecord;
import ssafy.authserv.domain.record.entity.SpeedRecord;
import ssafy.authserv.domain.record.repository.SoccerRecordRepository;
import ssafy.authserv.domain.record.repository.SpeedRecordRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
    private final SoccerRecordRepository soccerRepository;
    private final MemberRepository memberRepository;
    private final SpeedRecordRepository speedRecordRepository;

    @Override
    @Transactional
    @Async("threadPoolTaskExecutor")
    public void saveSoccerRecord(Member member){
        soccerRepository.save(
                SoccerRecord.builder()
                        .member(member)
                        .build()
        );
    }

    @Override
    @Transactional
    @Async("threadPoolTaskExecutor")
    public void testSaveSpeed(UUID memberId, int map, float time) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_USER));

        Optional<SpeedRecord> speedRecord = speedRecordRepository.findByMemberIdAndMap(memberId, map);
        if (speedRecord.isEmpty()) {
            speedRecordRepository.save(
                    SpeedRecord.builder()
                            .member(member)
                            .map(map)
                            .time(time)
                            .build()
            );
            return ;
        } else {
            SpeedRecord record = speedRecord.get();
            if (record.getTime() > time) {
                record.setTime(time);
                speedRecordRepository.save(record);
                return ;
            }
        }
    }
}
