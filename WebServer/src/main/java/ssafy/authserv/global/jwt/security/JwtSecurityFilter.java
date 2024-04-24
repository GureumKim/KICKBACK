package ssafy.authserv.global.jwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ssafy.authserv.global.jwt.JwtTokenProvider;
import ssafy.authserv.global.jwt.exception.JwtException;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private static final String BEARER_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = getJwtFrom(request);

        if (StringUtils.hasText(accessToken)) {
            try {
                MemberLoginActive member = jwtTokenProvider.resolveAccessToken(accessToken);

                log.info("회원 ID : {} - 요청 시도: ", member.id());
                SecurityContextHolder.getContext()
                        .setAuthentication(createAuthenticationToken(member));
            } catch (JwtException e) {
                SecurityContextHolder.clearContext();
                throw new RuntimeException(e);
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFrom(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.info("요청 : {} / 액세스 토큰 값 : {}", request.getRequestURI(), bearerToken);

//        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
//            return bearerToken.substring(7); // BEARER_PREFIX 7자
//        }
        if (StringUtils.hasText(bearerToken)) {
            if (bearerToken.startsWith(BEARER_PREFIX)) {
                return bearerToken.substring(7);
            }
            return bearerToken; // BEARER_PREFIX 7자
        }

        return null;
        // 코드 줄이면 이렇게
//        return StringUtils.hasText(bearerToken) ? (bearerToken.startsWith(BEARER_PREFIX) ? bearerToken.substring(7) : bearerToken) : null;
    }

    private JwtAuthenticationToken createAuthenticationToken(MemberLoginActive user) {
        return new JwtAuthenticationToken(user, "", List.of(new SimpleGrantedAuthority(user.role().name())));
        // List.of("a", "b", "c") -> [a, b, c]
    }


}
