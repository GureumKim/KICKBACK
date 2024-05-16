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
//        String accessToken = getJwtFrom(request);

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
                    // Token is valid but no user found. This should be handled as per your application's security policy.
                    log.error("인증된 사용자 정보를 찾을 수 없습니다.");
//                    response.sendError(HttpStatus.UNAUTHORIZED.value(), "인증된 사용자 정보를 찾을 수 없습니다.");
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
//                response.sendError(e.getErrorCode().getHttpStatus().value(), e.getErrorCode().getErrorMessage());
//                return;

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

//        try {
//            filterChain.doFilter(request, response);
//        } catch(ServletException | NullPointerException e){
////            throw new ServletException(e);
//            throw new JwtException(JwtErrorCode.INVALID_CLAIMS);
//        }
        filterChain.doFilter(request, response);
    }
    private String getJwtFrom(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        log.info("요청 : {} / 액세스 토큰 값 : {}", request.getRequestURI(), bearerToken);

        if (StringUtils.hasText(bearerToken)) {
            if (bearerToken.startsWith(BEARER_PREFIX)) {
                return bearerToken.substring(7);
            }
            return bearerToken; // BEARER_PREFIX 7자
        }

        return null;
    }


    private String resolveTokenFromCookie(HttpServletRequest request) {
        // 사실 이 로직은 필요 없는 듯
        // 테스트 후 삭제하기
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken)) {
            if (bearerToken.startsWith(BEARER_PREFIX)) {
                return bearerToken.substring(7);
            }
            return bearerToken;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("accessToken")) {
//                    log.info("====!!!!!! {} !!!!===", cookie.getValue());
                    return cookie.getValue();
                }
            }
        }

        return null;
    }

    private JwtAuthenticationToken createAuthenticationToken(MemberLoginActive user) {
        return new JwtAuthenticationToken(user, "", List.of(new SimpleGrantedAuthority(user.role().name())));
        // List.of("a", "b", "c") -> [a, b, c]
    }

    private boolean isBlockedAccessToken(String email, String token) {
        List<String> blockedTokens = blockedAccessTokenService.findAllByEmail(email);
        // 각 요소에 대해 equals 메서드를 사용하여 지정된 문자열과 완벽하게 일치하는지 비교
        return blockedTokens.contains(token);
    }
}
