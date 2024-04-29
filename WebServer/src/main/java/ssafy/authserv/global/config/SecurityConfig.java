package ssafy.authserv.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.security.config.http.SessionCreationPolicy;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@EnableConfigurationProperties(JwtProps.class)
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    // 비밀번호 인코딩 방식을 BCrypt로 설정
    @Bean
    public PasswordEncoder passwordEncoder() { return new BCryptPasswordEncoder(); }

    // HTTP 요청에 대한 보안 구성 정의
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors ->
                        cors.configurationSource(corsConfigurationSource())) //CORS 설정을 활성화합니다.
//                .requiresChannel(channel ->
//                        channel.anyRequest().requiresSecure()) // 모든 요청에 대해 HTTPS를 요구합니다.
//                .authorizeHttpRequests((requests)->requests // 페이지 별로 요청 권한 분기 가능
//                        .requestMatchers("/myAccount").hasRole("USER")
//                        .requestMatchers("/myBalance").hasAnyRole("USER","ADMIN")
//                        .requestMatchers("/myLoans").authenticated()
//                        .requestMatchers("/myCards").hasRole("USER")
//                        .requestMatchers("/user").authenticated()
//                        .requestMatchers("/notices","/contact","/register").permitAll())
                .httpBasic(AbstractHttpConfigurer::disable)  // 기본 인증을 비활성화합니다.
                .headers(header ->
                        header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)) // Clickjacking 공격 방지를 위해 사용되는 X-Frame-Options 헤더를 비활성화합니다.
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().permitAll()) // 모든 요청에 대해 접근을 허용합니다.
                .formLogin(AbstractHttpConfigurer::disable) // 폼 기반 로그인을 비활성화합니다.
                .logout(AbstractHttpConfigurer::disable) // 로그아웃 처리를 비활성화합니다.
                .addFilterBefore(jwtSecurityFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public JwtSecurityFilter jwtSecurityFilter() { return new JwtSecurityFilter(jwtTokenProvider, objectMapper); }

    // CORS 필터를 등록합니다. 외부 도메인에서의 API 요청을 허용하기 위한 구성입니다.
    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilterRegistrationBean() {
        CorsConfiguration config = getCorsConfiguration(6000L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 애플리케이션의 모든 경로("/**")에 대해 CORS 구성을 적용합니다.
        source.registerCorsConfiguration("/**", config);  // 애플리케이션의 모든 경로에 대해 CORS 설정을 적용합니다.
        FilterRegistrationBean<CorsFilter> filterBean = new FilterRegistrationBean<>(new CorsFilter(source));
        // 필터 체인에서의 실행 순서를 설정. 숫자가 낮을수록 먼저 실행.
        filterBean.setOrder(0); // 필터 체인에서의 순서 설정
        return filterBean;
    }

    // CORS 설정을 위한 Bean을 정의합니다. CORS는 외부 도메인에서 리소스를 안전하게 요청할 수 있게 해주는 메커니즘입니다.
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = getCorsConfiguration(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 애플리케이션의 모든 경로 ("/**")에 대해 CORS 구성 적용
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // CORS 구성을 생성합니다. 여기서는 모든 출처, 헤더, 메소드를 허용하며, 사전 요청의 Max Age를 설정합니다.
    private CorsConfiguration getCorsConfiguration(long maxAge) {

        CorsConfiguration configuration = new CorsConfiguration();

        List<String> allowedOrigins = Arrays.asList("http://localhost:5173");

        configuration.setAllowedOrigins(allowedOrigins);
        configuration.setAllowedMethods(Collections.singletonList("*"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(maxAge);

        // 여기에 refreshToken 등을 넣는 방법은 별로 좋지 않음
        // http only 쿠키를 사용해 인가를 확인하는 것이 더 일반적
        // 차후 "Authorization 헤더 말고 제거하기
        configuration.setExposedHeaders(Arrays.asList("Authorization", "refreshToken", "accessToken"));

        return configuration;
    }

    // 보안 검사를 무시하는 WebSecurityCustomizer를 구성합니다. 이 부분은 주의 깊게 사용해야 합니다.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().anyRequest(); // 모든 요청을 무시합니다. 실제 환경에서는 이러한 설정을 사용하지 않는 것이 좋습니다.
    }

}
