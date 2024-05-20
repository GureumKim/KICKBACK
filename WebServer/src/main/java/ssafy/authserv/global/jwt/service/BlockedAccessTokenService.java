package ssafy.authserv.global.jwt.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ssafy.authserv.global.jwt.entity.BlockedAccessToken;
import ssafy.authserv.global.jwt.repository.BlockedAccessTokenRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlockedAccessTokenService {
    private final BlockedAccessTokenRepository blockedAccessTokenRepository;

    @Value("${jwt.accessExpiration}")
    private Duration BLOCKING_DURATION;

    /**
     * 블록된 접근 토큰을 저장합니다.
     *
     * @param email 토큰과 연관된 사용자 이메일
     * @param token 저장할 블록된 토큰
     */
    @Transactional
    public void save(String email, String token) {
        // 토큰의 만료 시간을 계산합니다.
        long expirationTime = Instant.now().plus(BLOCKING_DURATION).toEpochMilli();
        // 해당 이메일에 이미 토큰이 발급되었었던 것이니까 추가 확인은 하지 않습니다.
        BlockedAccessToken blockedAccessToken = BlockedAccessToken.builder()
                .email(email)
                .token(token)
                .expirationTime(expirationTime)
                .build();
        blockedAccessTokenRepository.save(blockedAccessToken);
    }

    /**
     * 주어진 이메일에 대한 모든 블록된 접근 토큰을 찾습니다.
     *
     * @param email 블록된 토큰을 찾을 사용자 이메일
     * @return 유효한 블록된 토큰 목록
     */
    @Transactional
    public List<String> findAllByEmail(String email){
        // 현재 시간을 밀리초로 가져옵니다.
        long now = Instant.now().toEpochMilli();
        // 만료된 토큰을 삭제합니다.
        blockedAccessTokenRepository.deleteExpiredTokensByEmail(now, email);
        // 주어진 이메일에 대한 모든 블록된 토큰을 찾습니다.
        List<BlockedAccessToken> blockedTokens = blockedAccessTokenRepository.findAllByEmail(email);

        // 유효한 토큰만 필터링하여 반환합니다.
        return blockedTokens.stream()
                .filter(token -> token.getExpirationTime() > now)
                .map(BlockedAccessToken::getToken)
                .toList();
    }


}
