package ssafy.authserv.domain.record.dto;

import lombok.Builder;
import ssafy.authserv.domain.member.entity.Member;

@Builder
public record SoccerRankingInfo(
        String nickname,
        String profileImage,
        int wins
) {
    public static SoccerRankingInfo convertToDTO(Member member, int wins) {
        return SoccerRankingInfo.builder()
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .wins(wins)
                .build();
    }
}
