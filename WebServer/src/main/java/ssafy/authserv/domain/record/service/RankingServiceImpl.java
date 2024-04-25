package ssafy.authserv.domain.record.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.repository.MemberRepository;
import ssafy.authserv.domain.record.dto.SoccerRankingInfo;
import ssafy.authserv.domain.record.entity.SoccerRecord;
import ssafy.authserv.domain.record.repository.SoccerRecordRepository;
import ssafy.authserv.domain.record.service.RankingService;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {
    private final MemberRepository memberRepository;
    private final SoccerRecordRepository soccerRecordRepository;

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
}
