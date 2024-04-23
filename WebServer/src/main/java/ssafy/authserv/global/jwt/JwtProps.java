package ssafy.authserv.global.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

/**
 * Not registered via @EnableConfigurationProperties 문제
 * SecruityConfig에 @EnableConfigurationProperties(JwtProps.class) 사용하여 해결
 *
 * 참조 1.
 * https://www.inflearn.com/questions/1138421/%ED%98%84%EC%9E%AC-springboot-3-2-1-%EB%B6%80%ED%84%B0-configurationproperties-%EB%A5%BC-%EC%82%AC%EC%9A%A9%ED%95%9C-%EC%84%A4%EC%A0%95%EA%B0%92-%EB%B0%94%EC%9D%B8%EB%94%A9%EC%97%90-%EC%9D%B4%EC%8A%88%EA%B0%80-%EC%9E%88%EB%8A%94%EA%B2%83-%EA%B0%99%EC%8A%B5%EB%8B%88%EB%8B%A4
 *
 * 참조 2.
 * https://docs.google.com/document/d/1j0jcJ9EoXMGzwAA2H0b9TOvRtpwlxI5Dtn3sRtuXQas/edit#heading=h.b1yk4ued1pxo
 * */
@ConfigurationProperties(prefix = "jwt")
public record JwtProps(
        String accessKey,
        Duration accessExpiration,
        String refreshKey,
        Duration refreshExpiration
) {
}
