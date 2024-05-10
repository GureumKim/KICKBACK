package org.example.businessserver.handler;

import org.example.businessserver.message.ResponseToMsgPack;
import org.example.businessserver.object.*;
import org.example.businessserver.service.Broadcast;
import org.msgpack.core.MessageUnpacker;
import reactor.netty.NettyInbound;

import java.io.IOException;

public class LobbyHandler {
    public static void initialLogIn(NettyInbound in, MessageUnpacker unPacker) {
        in.withConnection(connection -> {
            // 로비 채널 가져오기
            Channel lobby = Channels.getOrCreateChannel("lobby");
            // 유저네임 가져오기
            String userName;

            try {
                userName = unPacker.unpackString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // 세션 객체 생성 및 전체 세션 목록에 추가
            Session client = new Session(connection, userName, "lobby");
            Sessions.getInstance().put(connection, client);

            // 로비에 세션 추가
            lobby.addUserSession(userName, client);

            try {
                Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyUserToMsgPack(lobby)).subscribe();
                Broadcast.broadcastPrivate(connection, ResponseToMsgPack.lobbyRoomToMsgPack()).then().subscribe();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
