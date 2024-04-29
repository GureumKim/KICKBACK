package ssafy.authserv.domain.member.dto;

import lombok.Builder;
import ssafy.authserv.domain.member.entity.enums.MemberRole;

import java.util.UUID;

@Builder
public record MemberInfo(
        MemberRole role,
        String nickname,
        String profileImage
) {
}
