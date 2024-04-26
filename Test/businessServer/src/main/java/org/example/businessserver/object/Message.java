package org.example.businessserver.object;


import org.msgpack.core.MessageBufferPacker;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;

public class Message {
    public static byte[] packing() throws IOException {
        MessageBufferPacker packer = MessagePack.newDefaultBufferPacker();
        packer.packString("test");

        byte[] bytes = packer.toByteArray();
        packer.close();

        return bytes;
    }
}
