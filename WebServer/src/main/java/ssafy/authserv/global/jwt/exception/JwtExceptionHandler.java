package ssafy.authserv.global.jwt.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ssafy.authserv.global.common.dto.Message;

import java.io.IOException;

@Controller
@Slf4j
public class JwtExceptionHandler {

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<Message<String>> handleJwtException(JwtException e, HttpServletResponse response) throws IOException {
        // JwtException에서 errorCode를 추출하여 해당하는 HttpStatus를 응답으로 반환
        log.error("에러: {}", e.getMessage());
        response.sendError(e.getErrorCode().getHttpStatus().value());
        return ResponseEntity.status(e.getErrorCode().getHttpStatus().value()).body(Message.fail(e.getErrorCode().getHttpStatus().toString(),e.getErrorCode().getErrorMessage()));
    }
}
