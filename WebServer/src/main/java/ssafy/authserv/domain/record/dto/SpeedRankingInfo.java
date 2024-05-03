package ssafy.authserv.domain.record.dto;

import lombok.Builder;
import ssafy.authserv.domain.member.entity.Member;

import java.util.concurrent.atomic.AtomicLong;

@Builder
public record SpeedRankingInfo(
        long rank,
        String nickname,
        String profileImage,
        String time
) {
    public static SpeedRankingInfo convertToDTO(Member member, String time, long ranking) {
        return SpeedRankingInfo.builder()
                .rank(ranking)
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .time(time)
                .build();
    }
}
