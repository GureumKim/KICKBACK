package ssafy.authserv.domain.record.dto;

import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.record.entity.SpeedRecord;

import java.time.Duration;

public record BetaSpeedRankingInfo(
        Long rank,
        String nickname,
        String profileImage,
        String time
) {
}
