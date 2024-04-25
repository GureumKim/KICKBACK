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

    public static void unpacking(byte[] bytes) throws IOException {
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(bytes);

        System.out.println(unpacker.getNextFormat().getValueType());

        try {
            System.out.println(unpacker.unpackString());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("done");
    }
}
