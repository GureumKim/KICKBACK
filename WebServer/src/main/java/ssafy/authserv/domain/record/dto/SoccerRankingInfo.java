package ssafy.authserv.domain.record.dto;

import lombok.Builder;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.record.entity.SoccerRecord;

@Builder
public record SoccerRankingInfo(
        Long rank,
        String nickname,
        String profileImage,
        int wins,
        int draws,
        int loses,
        int scores,
        int gd
) {
    public static SoccerRankingInfo convertToDTO(SoccerRecord record, Long rank) {
        return SoccerRankingInfo.builder()
                .rank(rank)
                .nickname(record.getMember().getNickname())
                .profileImage(record.getMember().getProfileImage())
                .wins(record.getWins())
                .draws(record.getDraws())
                .loses(record.getLoses())
                .scores(record.getScores())
                .gd(record.getGd())
                .build();
    }
}
