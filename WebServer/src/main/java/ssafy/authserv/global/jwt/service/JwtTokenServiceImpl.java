package ssafy.authserv.global.jwt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ssafy.authserv.domain.member.dto.LoginResponse;
import ssafy.authserv.domain.member.dto.MemberInfo;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.exception.MemberErrorCode;
import ssafy.authserv.domain.member.exception.MemberException;
import ssafy.authserv.domain.member.repository.MemberRepository;
import ssafy.authserv.global.jwt.JwtTokenProvider;
import ssafy.authserv.global.jwt.dto.JwtToken;
import ssafy.authserv.global.jwt.repository.RefreshTokenRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberRepository memberRepository;

    @Override
    public LoginResponse issueAndSaveTokens(Member member) {
        String accessToken = jwtTokenProvider.issueAccessToken(member);
        String refreshToken = jwtTokenProvider.issueRefreshToken();
        log.info("== {} 회원에 대한 토큰 발급", member.getEmail());

        try {
            refreshTokenRepository.save(member.getEmail(), refreshToken);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        JwtToken jwtToken = new JwtToken(accessToken, refreshToken);
        MemberInfo memberInfo = MemberInfo.builder()
//                .id(member.getId())
                .role(member.getRole())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .build();
        return new LoginResponse(jwtToken, memberInfo);
    }

    @Override
    public String reissueAccessToken(String email) {
//        String refreshToken = refreshTokenRepository.findByEmail(email)
//                .orElseThrow(() -> new MemberException(MemberErrorCode.REDIS_NOT_TOKEN));

        if (refreshTokenRepository.findByEmail(email).isEmpty()){
            throw new MemberException(MemberErrorCode.REDIS_NOT_TOKEN);
        }
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_USER));

        return jwtTokenProvider.issueAccessToken(member);
    }
}
