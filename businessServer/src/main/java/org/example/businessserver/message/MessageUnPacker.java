package org.example.businessserver.message;

import org.example.businessserver.handler.LiveServerHandler;
import org.example.businessserver.handler.LobbyHandler;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import reactor.netty.NettyInbound;

import java.io.IOException;


public class MessageUnPacker {
    public static void changeMsg(NettyInbound in, byte[] request) throws IOException {
        MessageUnpacker unpacker = MessagePack.newDefaultUnpacker(request);
        try {
            int arrayLength = unpacker.unpackArrayHeader();
            Type type = Type.findByIndex(unpacker.unpackInt());

            switch (type) {
                case LIVESERVER:
                    LiveServerHandler.liveServerConnect(in,unpacker);
                    break;
                case CLIENT:
                    LobbyHandler.initialLogIn(in,unpacker);
                    break;
                case CREATE:

                    break;
                default:
                    throw new IOException("데이터 형식이 이상함");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
