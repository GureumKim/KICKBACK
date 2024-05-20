package ssafy.authserv.global.jwt.service;

import ssafy.authserv.domain.member.dto.LoginResponse;
import ssafy.authserv.domain.member.entity.Member;

public interface JwtTokenService {
    LoginResponse issueAndSaveTokens(Member member);

    String reissueAccessToken(String email);
}
