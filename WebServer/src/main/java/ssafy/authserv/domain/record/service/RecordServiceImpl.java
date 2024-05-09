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
//import ssafy.authserv.domain.record.entity.SoccerRecord;
import ssafy.authserv.domain.record.dto.UpdateSoccerRecordRequest;
import ssafy.authserv.domain.record.entity.SoccerRecord;
import ssafy.authserv.domain.record.entity.SpeedRecord;
import ssafy.authserv.domain.record.entity.enums.MapType;
//import ssafy.authserv.domain.record.repository.SoccerRecordRepository;
import ssafy.authserv.domain.record.entity.enums.SoccerGameResultType;
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
    public void updateSoccerRecord(UUID memberId, UpdateSoccerRecordRequest request) {
        SoccerRecord record = soccerRepository.findByMemberId(memberId)
                .orElseThrow(() -> new RuntimeException("레코드를 찾을 수 없습니다. 잘못된 유저 정보입니다."));

        switch (SoccerGameResultType.fromName(request.result())) {
            case WIN -> {
                record.setWins(record.getWins() +1 );
                record.setScores(record.getScores() +3);
            }
            case DRAW -> {
                record.setDraws(record.getDraws()+1);
                record.setScores(record.getScores() + 1);
            }
            case LOSE -> record.setDraws(record.getLoses()+1);
        }

        record.setGd(record.getGd() + request.gd());
        soccerRepository.save(record);
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
