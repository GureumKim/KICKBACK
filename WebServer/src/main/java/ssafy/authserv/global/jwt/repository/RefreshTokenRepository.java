package ssafy.authserv.global.jwt.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import ssafy.authserv.global.jwt.JwtProps;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String KEY_PREFIX = "refreshToken::";
    private final JwtProps jwtProps;

    public void save(String email, String token) {
        String key = KEY_PREFIX + email;
        redisTemplate.opsForValue().set(key, token, jwtProps.refreshExpiration());
    }

    public Optional<String> findByEmail(String email){
        String token = redisTemplate.opsForValue().get(KEY_PREFIX + email);
        return Optional.ofNullable(token);
    }

    public void delete(String email) { redisTemplate.delete(KEY_PREFIX + email); }
}
