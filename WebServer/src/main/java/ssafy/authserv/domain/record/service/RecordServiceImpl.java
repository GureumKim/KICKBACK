package ssafy.authserv.domain.record.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.exception.MemberErrorCode;
import ssafy.authserv.domain.member.exception.MemberException;
import ssafy.authserv.domain.member.repository.MemberRepository;
import ssafy.authserv.domain.record.dto.BetaSpeedRankingInfo;
import ssafy.authserv.domain.record.entity.SoccerRecord;
import ssafy.authserv.domain.record.entity.SpeedRecord;
import ssafy.authserv.domain.record.entity.enums.MapType;
import ssafy.authserv.domain.record.repository.SoccerRecordRepository;
import ssafy.authserv.domain.record.repository.SpeedRecordRepository;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
    private final SoccerRecordRepository soccerRepository;
    private final MemberRepository memberRepository;
    private final SpeedRecordRepository speedRecordRepository;
    private final RankingUtils rankingUtils;

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
    public void updateSpeedRecord(UUID memberId, String mapName, String time) {
        Member member = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_USER));

        int mapOrdinal = MapType.getOrdinalByName(mapName);
        long millis = rankingUtils.stringToMillis(time);
        Optional<SpeedRecord> speedRecord = speedRecordRepository.findByMemberIdAndMap(memberId, mapOrdinal);
        if (speedRecord.isEmpty()) {
            speedRecordRepository.save(
                    SpeedRecord.builder()
                            .member(member)
                            .map(mapOrdinal)
                            .millis(millis)
                            .build()
            );
        } else {
            SpeedRecord record = speedRecord.get();
            if (record.getMillis() > millis) {
                record.setMillis(millis);
                speedRecordRepository.save(record);
            }
        }
    }
}
