package ssafy.authserv.domain.record.dto;

import lombok.Builder;
import ssafy.authserv.domain.member.entity.Member;

@Builder
public record SpeedRankingInfo(
        String nickname,
        String profileImage,
        String time
) {
    public static SpeedRankingInfo convertToDTO(Member member, String time) {
        return SpeedRankingInfo.builder()
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .time(time)
                .build();
    }
}
