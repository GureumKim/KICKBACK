package ssafy.authserv.domain.record.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.exception.MemberErrorCode;
import ssafy.authserv.domain.member.exception.MemberException;
import ssafy.authserv.domain.member.repository.MemberRepository;
import ssafy.authserv.domain.record.dto.BetaSpeedRankingInfo;
import ssafy.authserv.domain.record.dto.SoccerRankingInfo;
import ssafy.authserv.domain.record.dto.SpeedRankingInfo;
//import ssafy.authserv.domain.record.entity.SoccerRecord;
import ssafy.authserv.domain.record.entity.SoccerRecord;
import ssafy.authserv.domain.record.entity.SpeedRecord;
//import ssafy.authserv.domain.record.repository.SoccerRecordRepository;
import ssafy.authserv.domain.record.repository.SoccerRecordRepository;
import ssafy.authserv.domain.record.repository.SpeedRecordRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {
    private final MemberRepository memberRepository;
    private final SoccerRecordRepository soccerRecordRepository;
    private final SpeedRecordRepository speedRecordRepository;
    private final RankingUtils rankingUtils;

//    @Override
//    public Page<Member> getSoccerRecords(int pageNum) {
//        // 페이지 당 10 명
//        return memberRepository.findMembersBySoccerWins(PageRequest.of(pageNum,10));
//    }

//    @Override
//    @Transactional
//    public Page<SoccerRankingInfo> getSoccerRanking(int pageNum){
//        // 페이지 당 10명
//        Page<SoccerRecord> rankings =  soccerRecordRepository.findSoccerRecordsByWins(PageRequest.of(pageNum, 10));
//
//        return rankings.map(ranking -> {
//            return SoccerRankingInfo.convertToDTO(ranking.getMember(), ranking.getWins() );
//        }
//        );
//    }

    @Override
    @Transactional
    public Page<SpeedRankingInfo> getSpeedRanking(int mapNum, int pageNum){
        Page<SpeedRecord> rankings = speedRecordRepository.findSpeedRankingsByMap(mapNum, PageRequest.of(pageNum, 10));

        AtomicLong rankCounter = new AtomicLong(pageNum * 10L);

        return rankings.map(ranking -> {
            long rank = rankCounter.getAndIncrement();
            return SpeedRankingInfo.convertToDTO(ranking.getMember(), rankingUtils.millisToString(ranking.getMillis()), rank);
        });
    }

    @Override
    @Transactional
    public List<SpeedRankingInfo> getAllSpeedRanking(int mapNum) {
        List<SpeedRecord> rankings = speedRecordRepository.findAllSpeedRecordsByMap(mapNum);
        AtomicLong rankCounter = new AtomicLong(1);

        return rankings.stream().map(ranking -> {
            long rank = rankCounter.getAndIncrement();
            return SpeedRankingInfo.convertToDTO(ranking.getMember(), rankingUtils.millisToString(ranking.getMillis()), rank);
        }).collect(Collectors.toList());
    }

    @Override
    public BetaSpeedRankingInfo getMemberSpeedRanking(int map, String nickname) {
        List<Object[]> rankingInfo = speedRecordRepository.findRecordAndRankByNicknameAndMap(nickname, map);
        Member member = memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_USER));

        if (!rankingInfo.isEmpty()) {
            Object[] data = rankingInfo.get(0);
            SpeedRecord record = (SpeedRecord) data[0];
            Long rank = (Long) data[1] + 1;

            return new BetaSpeedRankingInfo(rank, nickname, member.getProfileImage(), rankingUtils.millisToString(record.getMillis()));
         }

       return new BetaSpeedRankingInfo(null, nickname, member.getProfileImage(), "기록이 없습니다.");
    }

    @Override
    @Transactional
    public List<SoccerRankingInfo> getSoccerRanking() {
        List<SoccerRecord> rankings = soccerRecordRepository.getRankings();
        AtomicLong rankCounter = new AtomicLong(1);
        return rankings.stream().map(ranking -> {
            long rank = rankCounter.getAndIncrement();
            return SoccerRankingInfo.convertToDTO(ranking, rank);
        }).toList();
    }

}
