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

    @Transactional
    public void save(String email, String token) {
        long expirationTime = Instant.now().plus(BLOCKING_DURATION).toEpochMilli();
        // 해당 이메일에 이미 토큰이 발급되었었던 것이니까 추가 확인은 하지 않음
        BlockedAccessToken blockedAccessToken = BlockedAccessToken.builder()
                .email(email)
                .token(token)
                .expirationTime(expirationTime)
                .build();
        blockedAccessTokenRepository.save(blockedAccessToken);
    }

    @Transactional
    public List<String> findAllByEmail(String email){
        long now = Instant.now().toEpochMilli();
        blockedAccessTokenRepository.deleteExpiredTokensByEmail(now, email);
        List<BlockedAccessToken> blockedTokens = blockedAccessTokenRepository.findAllByEmail(email);

        return blockedTokens.stream()
                .filter(token -> token.getExpirationTime() > now)
                .map(BlockedAccessToken::getToken)
                .collect(Collectors.toList());
    }


}
