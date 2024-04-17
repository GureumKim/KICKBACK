package ssafy.authserv.global.jwt.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum JwtErrorCode {
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료돼쓰" ),
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, "사용할 수 없는 토큰쓰"),
    SIGNATURE_INVALID(HttpStatus.FORBIDDEN, "토큰의 서명 검증에 실패");

    private final HttpStatus httpStatus; // 에러 상황에 해당하는 HTTP 상태
    private final String errorMessage; // 에러 상황을 설명하는 메시지
}
