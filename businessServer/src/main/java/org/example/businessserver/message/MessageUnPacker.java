package org.example.businessserver.message;

import org.example.businessserver.handler.LiveServerHandler;
import org.example.businessserver.handler.LobbyHandler;
import org.example.businessserver.handler.RoomHandler;
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
                    System.out.println("Live Server");
                    LiveServerHandler.liveServerConnect(in, unpacker);
                    break;
                case CLIENT:
                    System.out.println("Client");
                    LobbyHandler.initialLogIn(in, unpacker);
                    break;
                case CREATE:
                    System.out.println("Create");
                    RoomHandler.createRoom(unpacker);
                    break;
                case JOIN:
                    RoomHandler.joinRoom(unpacker);
                    break;
                case LEVAE:
                    RoomHandler.leaveRoom(unpacker);
                    break;
                case READY:
                    RoomHandler.readyUser(unpacker);
                    break;
                case START:
                    System.out.println("START");
                    RoomHandler.startGame(unpacker);
                    break;
                case ITEM:
                    break;
                case END:
                    RoomHandler.endGame(unpacker);
                    break;
                case Map:
                    RoomHandler.changeMap(unpacker);
                    break;
                default:
                    throw new IOException("Invalid Message");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
