package ssafy.authserv.domain.member.dto;

import ssafy.authserv.global.jwt.dto.JwtToken;

public record LoginResponse(JwtToken jwtToken, MemberInfo memberInfo) {
}
