package ssafy.authserv.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import ssafy.authserv.global.jwt.JwtProps;
import ssafy.authserv.global.jwt.JwtTokenProvider;
import ssafy.authserv.global.jwt.security.JwtSecurityFilter;
import ssafy.authserv.global.jwt.service.BlockedAccessTokenService;

import java.util.Arrays;
import java.util.List;

@EnableConfigurationProperties(JwtProps.class)
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final BlockedAccessTokenService blockedAccessTokenService;

    // 비밀번호 인코딩 방식을 BCrypt로 설정
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    // HTTP 요청에 대한 보안 구성 정의
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // CORS 설정을 활성화합니다.
                .cors(cors ->
                        cors.configurationSource(corsConfigurationSource())) //CORS 설정을 활성화합니다.
                // 모든 요청에 대해 HTTPS를 요구합니다.
                .requiresChannel(channel ->
                        channel.anyRequest().requiresSecure()) // 모든 요청에 대해 HTTPS를 요구합니다.
                // 기본 인증을 비활성화합니다.
                .httpBasic(AbstractHttpConfigurer::disable)  // 기본 인증을 비활성화합니다.
                // Clickjacking 공격 방지를 위해 사용되는 X-Frame-Options 헤더를 비활성화합니다.
                .headers(header ->
                        header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // Clickjacking 공격 방지를 위해 사용되는 X-Frame-Options 헤더를 비활성화합니다.
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().permitAll()) // 모든 요청에 대해 접근을 허용합니다.
                .formLogin(AbstractHttpConfigurer::disable) // 폼 기반 로그인을 비활성화합니다.
                // 로그아웃 처리를 설정합니다.
                .logout(authz ->
                        authz.logoutUrl("/api/v1/member/logout")
                                .deleteCookies("JSESSIONID")
                                .clearAuthentication(true))
                // JWT 필터를 UsernamePasswordAuthenticationFilter 앞에 추가합니다.
                .addFilterBefore(jwtSecurityFilter(), UsernamePasswordAuthenticationFilter.class
                );
        return http.build();
    }

    // JWT 보안 필터를 빈으로 정의합니다.
    @Bean
    public JwtSecurityFilter jwtSecurityFilter() { return new JwtSecurityFilter(jwtTokenProvider, objectMapper, blockedAccessTokenService); }

    // 외부 도메인에서의 API 요청을 허용하기 위해 CORS 필터를 등록합니다.
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {

        CorsConfigurationSource source = corsConfigurationSource();
        FilterRegistrationBean<CorsFilter> filterBean = new FilterRegistrationBean<>(new CorsFilter(source));
        // 필터 체인에서의 실행 순서를 설정. 숫자가 낮을수록 먼저 실행.
        filterBean.setOrder(0); // 필터 체인에서의 순서 설정
        return filterBean;
    }

    // CORS 설정을 위한 Bean을 정의합니다. CORS는 외부 도메인에서 리소스를 안전하게 요청할 수 있게 해주는 메커니즘입니다.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = getCorsConfiguration(6 * 60 * 60 * 1000L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 애플리케이션의 모든 경로 ("/**")에 대해 CORS 구성 적용
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // CORS 설정을 생성합니다. 여기서는 모든 출처, 헤더, 메소드를 허용하며, 사전 요청의 Max Age를 설정합니다.
    private CorsConfiguration getCorsConfiguration(long maxAge) {

        CorsConfiguration configuration = new CorsConfiguration();

        List<String> allowedOrigins = Arrays.asList("http://localhost:3000", "https://k10c209.p.ssafy.io");

        // 허용된 출처 설정
        configuration.setAllowedOrigins(allowedOrigins);
        // 허용된 HTTP 메서드 설정
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "DELETE", "PUT"));
        // 자격 증명 허용 설정
        configuration.setAllowCredentials(true);
        // 허용된 헤더 설정
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        // 사전 요청의 최대 캐시 시간 설정
        configuration.setMaxAge(maxAge);
        // 노출할 헤더 설정
        configuration.setExposedHeaders(Arrays.asList("Authorization", "refreshToken", "accessToken"));

        return configuration;
    }

    // 보안 검사를 무시하는 WebSecurityCustomizer를 구성합니다. 이 부분은 주의 깊게 사용해야 합니다.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().anyRequest(); // 모든 요청을 무시합니다. 실제 환경에서는 이러한 설정을 사용하지 않는 것이 좋습니다.
    }

}
