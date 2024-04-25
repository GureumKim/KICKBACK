package org.example.businessserver.message;

import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;

import java.io.IOException;

public class MessagePacker {
    public static byte[] packing(Type type) throws IOException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();

        try {
            packer.packString(type.name());

            return packer.toByteArray();
        } finally {
            packer.close();
        }
    }
}
