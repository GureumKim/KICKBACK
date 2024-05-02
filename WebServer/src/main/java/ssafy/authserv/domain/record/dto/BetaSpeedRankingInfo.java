package ssafy.authserv.domain.record.dto;

import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.record.entity.SpeedRecord;

import java.time.Duration;

public record BetaSpeedRankingInfo(
        String nickname,
        String profileImage,
        String time
) {
}
