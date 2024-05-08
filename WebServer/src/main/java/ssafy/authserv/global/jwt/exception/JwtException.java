package ssafy.authserv.global.jwt.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Getter
public class JwtException extends RuntimeException {
    private final JwtErrorCode errorCode;

    /**
     * super()의 사용
     * super() 키워드는 부모 클래스의 생성자를 호출하는 데 사용됩니다. 여기서 super(errorCode.getErrorMessage()); 호출은 JwtException의 부모 클래스인 RuntimeException의 생성자를 호출하여, 예외 메시지로 errorCode에서 제공하는 메시지를 설정합니다. 이러한 방식으로, 예외가 발생할 때 제공되는 메시지를 통해 오류 상황을 더 명확히 할 수 있습니다.
     *
     * 이 구조를 사용함으로써, 다음과 같은 이점을 얻습니다:
     *
     * 명확한 오류 처리: 각 JWT 오류 상황에 대해 명확하게 정의된 코드와 메시지를 사용하여, 오류 처리 로직을 보다 명확하고 일관되게 구현할 수 있습니다.
     * 재사용성 및 유지보수성: 오류 유형을 JwtErrorCode enum으로 관리함으로써, 코드의 재사용성과 유지보수성을 향상시킵니다. 오류 유형이 추가되거나 변경되어야 할 때, enum과 관련 예외 처리 코드만 수정하면 되므로, 관련 코드를 쉽게 관리할 수 있습니다.
     * 확장성: 새로운 JWT 오류 유형이 필요한 경우, JwtErrorCode enum에 새로운 상수를 추가하기만 하면 됩니다. 이는 확장성 있는 오류 처리 메커니즘을 제공합니다.
     */
    public JwtException(JwtErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}
