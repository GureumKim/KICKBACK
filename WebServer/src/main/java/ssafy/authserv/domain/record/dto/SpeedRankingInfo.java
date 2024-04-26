package ssafy.authserv.domain.record.dto;

import lombok.Builder;
import ssafy.authserv.domain.member.entity.Member;

@Builder
public record SpeedRankingInfo(
        String nickname,
        String profileImage,
        float time
) {
    public static SpeedRankingInfo convertToDTO(Member member, float time) {
        return SpeedRankingInfo.builder()
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .time(time)
                .build();
    }
}
