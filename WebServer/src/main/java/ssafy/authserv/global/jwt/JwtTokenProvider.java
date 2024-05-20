package ssafy.authserv.global.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.entity.enums.MemberRole;
import ssafy.authserv.global.jwt.exception.JwtErrorCode;
import ssafy.authserv.global.jwt.exception.JwtException;
import ssafy.authserv.global.jwt.security.MemberLoginActive;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    private final JwtProps jwtProps;
    private static final String CLAIM_EMAIL = "email";
    private static final String CLAIM_NICKNAME = "nickname";
    private static final String CLAIM_PROFILE_IMAGE = "profileImage";
    private static final String CLAIM_ROLE = "role";

    // Access Token을 발급합니다.
    public String issueAccessToken(Member member) {
        Claims claims = Jwts.claims()
                .id(String.valueOf(member.getId()))
                .add(CLAIM_EMAIL, member.getEmail())
                .add(CLAIM_NICKNAME, member.getNickname())
                .add(CLAIM_ROLE, member.getRole())
                .build();

        return issueToken(claims, jwtProps.accessExpiration(), jwtProps.accessKey());
    }

    // RefreshToken을 발급합니다
    public String issueRefreshToken() { return issueToken(null, jwtProps.refreshExpiration(), jwtProps.refreshKey()); }

    private String issueToken(Claims claims, Duration expiration, String secretKey) {
        Date now = new Date();

        return Jwts.builder()
                .claims(claims)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + expiration.toMillis()))
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                .compact();
    }

    // Token을 검증합니다.
    private Claims resolveToken(String token, String secretKey) {
        Claims payload;

        try {
            // 토큰 파싱하여 payload 반환. 이 과정에서 토큰의 무결성과 유효성이 검증됨
            payload = Jwts.parser()
                    .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
                    .build()
                    .parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            // 토큰 만료 예외 처리
            throw new JwtException(JwtErrorCode.EXPIRED_TOKEN);
        } catch (MalformedJwtException | SecurityException | IllegalArgumentException e) {
            // 토큰 형식 불일치 예외 처리
            throw new JwtException(JwtErrorCode.INVALID_TOKEN);
        } catch (SignatureException e) {
            // 토큰 서명 검증 실패 예외 처리
            throw new JwtException(JwtErrorCode.SIGNATURE_INVALID);
        }

        return payload;
    }

    // resoleveToken 메서드를 활용하여 Access Token을 검증하고 유저 정보를 불러옵니다.
    public MemberLoginActive resolveAccessToken(String accessToken){
        Claims payload = resolveToken(accessToken, jwtProps.accessKey());

        try {
            return MemberLoginActive.builder()
                    .id(UUID.fromString(payload.getId()))
                    .email(payload.get(CLAIM_EMAIL, String.class))
                    .nickname(payload.get(CLAIM_NICKNAME, String.class))
                    .profileImage(payload.get(CLAIM_PROFILE_IMAGE, String.class))
                    .role(MemberRole.fromName(payload.get(CLAIM_ROLE, String.class)))
                    .build();
        } catch(IllegalArgumentException | NullPointerException e) {
            throw new JwtException(JwtErrorCode.INVALID_CLAIMS);
        }
    }


}
