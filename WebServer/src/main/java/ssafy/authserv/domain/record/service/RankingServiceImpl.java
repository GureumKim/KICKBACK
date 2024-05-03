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
import ssafy.authserv.domain.record.entity.SoccerRecord;
import ssafy.authserv.domain.record.entity.SpeedRecord;
import ssafy.authserv.domain.record.repository.SoccerRecordRepository;
import ssafy.authserv.domain.record.repository.SpeedRecordRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {
    private final MemberRepository memberRepository;
    private final SoccerRecordRepository soccerRecordRepository;
    private final SpeedRecordRepository speedRecordRepository;
    private final RankingUtils rankingUtils;

    @Override
    public Page<Member> getSoccerRecords(int pageNum) {
        // 페이지 당 10 명
        return memberRepository.findMembersBySoccerWins(PageRequest.of(pageNum,10));
    }

    @Override
    @Transactional // 이걸로 해결
    public Page<SoccerRankingInfo> getSoccerRanking(int pageNum){
        // 페이지 당 10명
        Page<SoccerRecord> rankings =  soccerRecordRepository.findSoccerRecordsByWins(PageRequest.of(pageNum, 10));

        return rankings.map(ranking -> {
            return SoccerRankingInfo.convertToDTO(ranking.getMember(), ranking.getWins() );
        }
        );
    }

    @Override
    @Transactional
    public Page<SpeedRankingInfo> getSpeedRanking(int mapNum, int pageNum){
        Page<SpeedRecord> rankings = speedRecordRepository.findTopRecordsByMap(mapNum, PageRequest.of(pageNum, 10));

        AtomicLong idx = new AtomicLong(pageNum);
        return rankings.map(ranking -> {
            idx.getAndIncrement();
            return SpeedRankingInfo.convertToDTO(ranking.getMember(), rankingUtils.millisToString(ranking.getMillis()), idx);
        });
    }

    @Override
    public BetaSpeedRankingInfo getMemberSpeedRanking(int map, String nickname) {
//       long ranking = speedRecordRepository.countBetterRecordsByNicknameAndMap(nickname, map) + 1;
//
//       Member member = memberRepository.findByNickname(nickname).orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_USER));

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
}
