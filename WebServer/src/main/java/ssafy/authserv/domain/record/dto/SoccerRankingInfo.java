package ssafy.authserv.domain.record.dto;

import lombok.Builder;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.record.entity.SoccerRecord;

@Builder
public record SoccerRankingInfo(
        String nickname,
        String profileImage,
        int wins,
        int draws,
        int loses,
        int scores,
        int gd
) {
    public static SoccerRankingInfo convertToDTO(Member member, SoccerRecord record) {
        return SoccerRankingInfo.builder()
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .wins(record.getWins())
                .draws(record.getDraws())
                .loses(record.getLoses())
                .scores(record.getScores())
                .gd(record.getGd())
                .build();
    }
}
