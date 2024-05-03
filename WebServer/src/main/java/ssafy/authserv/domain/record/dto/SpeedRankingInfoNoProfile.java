package ssafy.authserv.domain.record.dto;

import ssafy.authserv.domain.member.entity.Member;

public record SpeedRankingInfoNoProfile(
        int rank,
        String nickname,
        String time
) {
    public static SpeedRankingInfoNoProfile convertToDTO(Member member, String time, int ranking) {
        return new SpeedRankingInfoNoProfile(ranking, member.getNickname(), time);
    }
}
