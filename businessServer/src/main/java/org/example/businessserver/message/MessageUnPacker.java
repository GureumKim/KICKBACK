package org.example.businessserver.message;

import org.json.JSONObject;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;

public class RequestToJson {
    public static JSONObject changeMsg(byte[] request) throws IOException {
        try (MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(request)) {
            // 배열 헤더를 읽어서 배열의 길이를 확인합니다.
            int arrayLength = unpacker.unpackArrayHeader();

            // 배열의 각 요소를 읽습니다.
            for (int i = 0; i < arrayLength; i++) {
                if (i == arrayLength - 1) {
                    return null;
                }
                // 값의 타입에 따라 적절한 unpack 메소드를 사용합니다.
                switch (unpacker.getNextFormat().getValueType()) {
                    case INTEGER:
                        System.out.println("정수: " + unpacker.unpackInt());
                        break;
                    case STRING:
                        System.out.println("문자열: " + unpacker.unpackString());
                        break;
                    default:
                        throw new IOException("지원하지 않는 데이터 타입");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return json;
    }
}
