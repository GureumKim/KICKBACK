package ssafy.authserv.domain.record.service;

import org.springframework.data.domain.Page;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.record.dto.BetaSpeedRankingInfo;
import ssafy.authserv.domain.record.dto.SoccerRankingInfo;
import ssafy.authserv.domain.record.dto.SpeedRankingInfo;
import ssafy.authserv.domain.record.dto.SpeedRankingInfoNoProfile;

import java.util.List;

public interface RankingService {
    Page<Member> getSoccerRecords(int pageNum);

    Page<SoccerRankingInfo> getSoccerRanking(int pageNum);

    Page<SpeedRankingInfo> getSpeedRanking(int mapNum, int pageNum);

    BetaSpeedRankingInfo getMemberSpeedRanking(int map, String nickname);

    List<SpeedRankingInfoNoProfile> getSpeedRankingNoProfile(int mapNum);
}
