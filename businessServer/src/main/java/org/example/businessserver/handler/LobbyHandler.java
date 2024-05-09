package org.example.businessserver.handler;

import org.example.businessserver.message.ResponseToMsgPack;
import org.example.businessserver.object.Channels;
import org.example.businessserver.object.UserSession;
import org.example.businessserver.service.Broadcast;
import org.msgpack.core.MessageUnpacker;
import reactor.netty.NettyInbound;

import java.io.IOException;

public class LobbyHandler {
    public static void initialLogIn(NettyInbound in, MessageUnpacker unPacker) {
        in.withConnection(connection -> {
            // 로비 채널 가져오기
            Channels.Channel lobby = Channels.getOrCreateChannel("lobby");
            // 유저네임 가져오기
            String userName = null;

            try {
                userName = unPacker.unpackString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // 세션 정보 만들기
            UserSession userInfo = new UserSession(userName, connection);

            // 로비에 유저 추가
            lobby.addUserSession(userName, userInfo);
            Channels.addConnectionInfo(connection, userName);

            try {
                Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyUserToMsgPack(lobby)).subscribe();
                Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyRoomToMsgPack()).subscribe();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
