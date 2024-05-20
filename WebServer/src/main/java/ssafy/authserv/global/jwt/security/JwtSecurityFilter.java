package ssafy.authserv.global.jwt.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import ssafy.authserv.global.common.dto.Message;
import ssafy.authserv.global.jwt.JwtTokenProvider;
import ssafy.authserv.global.jwt.exception.JwtErrorCode;
import ssafy.authserv.global.jwt.exception.JwtException;
import ssafy.authserv.global.jwt.service.BlockedAccessTokenService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final BlockedAccessTokenService blockedAccessTokenService;

    private static final String BEARER_PREFIX = "Bearer ";

    // 특정 url들에 대해 필터링을 다르게 처리하도록 하기 위한 선언
    private static final Set<String> EXACT_PATHS = new HashSet<>();
    private static final String RANKING_API_PREFIX = "/api/v1/ranking";
    private static final String NOTICE_API_PREFIX = "/api/v1/notice";
    private static final String BOARD_API_PREFIX = "/api/v1/board";

    static {
        EXACT_PATHS.add("/api/v1/member/login");
        EXACT_PATHS.add("/api/v1/member/reissue/accessToken");
        EXACT_PATHS.add("/api/v1/member/signup");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();
        String method = request.getMethod();

        // 다음 경로에서는 필터를 실행하지 않음
        if (EXACT_PATHS.contains(path) || path.startsWith(RANKING_API_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }
        else if (path.startsWith(NOTICE_API_PREFIX) || path.startsWith(BOARD_API_PREFIX)){
            if (method.equals("GET")) {
                filterChain.doFilter(request, response);
                return;
            }
        }


        String accessToken = resolveTokenFromCookie(request);
        if (StringUtils.hasText(accessToken)) {
            // Blocked된 token인지 유효성 검사
            try {
                MemberLoginActive member = jwtTokenProvider.resolveAccessToken(accessToken);
                if (member == null) {
                    log.error("인증된 사용자 정보를 찾을 수 없습니다.");
                    throw new JwtException(JwtErrorCode.INVALID_CLAIMS);
                } else {
                    log.info("회원 ID : {} - 요청 시도: ", member.id());
                    if (isBlockedAccessToken(member.email(), accessToken)) {
                        throw new JwtException(JwtErrorCode.EXPIRED_TOKEN);
                    }
                }
                SecurityContextHolder.getContext()
                        .setAuthentication(createAuthenticationToken(member));
            } catch (JwtException e) {
                SecurityContextHolder.clearContext();
                log.info("JWT 검증 실패: {}", e.getErrorCode().getErrorMessage());

                // Message 객체를 사용하여 응답을 구성
                Message<Void> errorMessage = Message.fail(
                        e.getErrorCode().getHttpStatus().toString(),
                        e.getErrorCode().getErrorMessage()
                );
                // HTTP 응답 설정
                response.setStatus(e.getErrorCode().getHttpStatus().value());
                response.setContentType("application/json;charset=UTF-8");
                // JSON 형태로 변환하여 응답 본문에 작성
                ObjectMapper mapper = new ObjectMapper();
                PrintWriter out = response.getWriter();
                out.print(mapper.writeValueAsString(errorMessage));
                out.flush();
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String resolveTokenFromCookie(HttpServletRequest request) {
        // 유니티와의 통신은 웹에서와 달리 쿠키를 활용하기 어렵기 때문에 해당 헤더를 통해
        // 인증 및 인가 수행합니다.
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken)) {
            if (bearerToken.startsWith(BEARER_PREFIX)) {
                return bearerToken.substring(7);
            }
            return bearerToken;
        }

        // 쿠키에서 "accessToken" 키의 value를 가져옵니다.
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    // 인증된 사용자 정보를 담는 JwtAuthentication 객체를 생성합니다.
    private JwtAuthenticationToken createAuthenticationToken(MemberLoginActive user) {
        return new JwtAuthenticationToken(user, "", List.of(new SimpleGrantedAuthority(user.role().name())));
        // List.of("a", "b", "c") -> [a, b, c]
    }

    // 전송된 accessToken이 새로운 로그인을 통해 만료되었는지 확인하기 위한 메서드입니다.
    private boolean isBlockedAccessToken(String email, String token) {
        List<String> blockedTokens = blockedAccessTokenService.findAllByEmail(email);
        // 각 요소에 대해 equals 메서드를 사용하여 지정된 문자열과 완벽하게 일치하는지 비교합니다.
        return blockedTokens.contains(token);
    }
}
