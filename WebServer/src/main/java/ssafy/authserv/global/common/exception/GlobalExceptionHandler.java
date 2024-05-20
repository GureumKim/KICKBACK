package ssafy.authserv.global.common.exception;

import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ssafy.authserv.global.common.dto.Message;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(ServletException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Message<Map<String, String>>> handleFilterChainServletExceptions(ServletException ex) {
        // 실패한 경우 Message 객체 생성
        Map<String, String> errors = new HashMap<>();
        String rootCause = String.valueOf(ex.getRootCause());
        String message = ex.getMessage();
        errors.put(rootCause, message);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Message.fail("유저 정보를 찾을 수 없습니다.", errors));
    }

    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Message<String>> handleFilterChainNullpointerExceptions(NullPointerException ex) {
        // 실패한 경우 Message 객체 생성
        Map<String, String> errors = new HashMap<>();
        String cause = String.valueOf(ex.getCause());
        String message = ex.getMessage();
        errors.put(cause, message);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Message.fail("Null Pointer Error", "정보를 찾지 못했습니다."));
    }



//    @ExceptionHandler(Exception.class) // 모든 예외를 처리
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ResponseEntity<Message<String>> handleInternalServerError(Exception ex) {
//        log.error("Internal Server Error: {}", ex.getMessage());
//
//        if (ex.getClass().equals(JwtException.class)){
//            return ResponseEntity
//                    .status(JwtErrorCode.EXPIRED_TOKEN.getHttpStatus().value())
//                    .body(Message.fail("Token Expired: ", "토큰이 만료되었습니다."));
//        }
////        Message<Void> errorMessage = Message.fail("INTERNAL_SERVER_ERROR", "Internal Server Error: " + ex.getMessage());
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(Message.fail("InternalServerError", ex.getMessage()));
//    }


     // 다른 예외 유형에 대한 처리기를 추가할 수 있습니다.

//    @ExceptionHandler(ServletException.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public static void handleExceptionAtFilter(HttpServletResponse response, ServletException e) throws IOException {
//        log.error("필터 체인 에러: {}", e.getMessage());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//
//        Message<Void> errorMessage = Message.fail("INTERNAL_SERVER_ERROR", e.getMessage());
//        try (PrintWriter writer = response.getWriter()) {
//            writer.write(new ObjectMapper().writeValueAsString(errorMessage));
//        }
//    }

}
