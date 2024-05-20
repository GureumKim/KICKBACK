package ssafy.authserv.global.jwt.security;

import lombok.Builder;
import ssafy.authserv.domain.member.entity.enums.MemberRole;

import java.util.UUID;

@Builder
public record MemberLoginActive(
        UUID id,
        MemberRole role,
        String email,
        String nickname,
        String profileImage
) {
}
