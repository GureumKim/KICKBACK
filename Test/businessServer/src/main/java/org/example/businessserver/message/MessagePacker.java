package org.example.businessserver.message;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

import java.io.IOException;

public class MessagePacker {
    public static byte[] packing(String message) throws IOException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();

        try {
            int temp = Type.CREATE.getType();
            System.out.println(temp);

//            packer.packInt(temp);

            packer.packString(message);

            byte[] send = packer.toByteArray();

            return send;
        } finally {
            System.out.println("send complete");
            packer.close();
        }
    }
}
