package ssafy.authserv.global.common.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Message<T> {

    private final DataHeader dataHeader;
    private final T dataBody;

    /**
     * 메시지의 헤더를 표현하는 클래스
     * */
    @Getter
    @Builder
    private static class DataHeader {
        private final int successCode;
        private final String resultCode;
        private final Object resultMessage;

        // 성공 시
        private static DataHeader success() {
            return DataHeader.builder()
                    .successCode(0)
                    .build();
        }

        // 실패 시
        private static DataHeader fail(String resultCode, Object resultMessage) {
            return DataHeader.builder()
                    .successCode(1)
                    .resultCode(resultCode)
                    .resultMessage(resultMessage)
                    .build();
        }

    }

    public static <T> Message<T> success(T dataBody){
        return Message.<T>builder()
                .dataHeader(DataHeader.success())
                .dataBody(dataBody)
                .build();
    }

    public static Message<Void> success(){
        return Message.<Void>builder()
                .dataHeader(DataHeader.success())
                .build();
    }

    public static <T> Message<T> fail(String resultCode, Object resultMessage){
        return Message.<T>builder()
                .dataHeader(DataHeader.fail(resultCode, resultMessage))
                .dataBody(null)
                .build();
    }
}
