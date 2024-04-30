package org.example.businessserver.message;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

import java.io.IOException;

public class MessagePacker {
    public static byte[] packing(int command, String message) throws IOException {
        // MessageBufferPacker를 사용하여 데이터를 패킹합니다.
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();

        try {
            // CUnit 객체를 MsgPack 형식으로 패킹합니다.
            // 패킹 순서는 command, message 순입니다.
            packer.packArrayHeader(2);
            packer.packInt(command);
            packer.packString(message);

            // 패킹된 데이터를 바이트 배열로 반환합니다.
            return packer.toByteArray();
        } finally {
            System.out.println("send complete");
            packer.close();
        }
    }
}