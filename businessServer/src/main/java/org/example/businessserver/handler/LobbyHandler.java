package org.example.businessserver.handler;

import org.example.businessserver.message.MessagePacker;
import org.example.businessserver.message.ResponseToJson;
import org.example.businessserver.object.Channels;
import org.example.businessserver.object.UserSession;
import org.example.businessserver.service.Broadcast;
import org.json.JSONObject;
import reactor.netty.NettyInbound;

import java.io.IOException;

public class LobbyHandler {
    public static void initialLogIn(NettyInbound in, JSONObject json) {
        in.withConnection(connection -> {

            // 로비 채널 가져오기
            Channels.Channel lobby = Channels.getOrCreateChannel("lobby");

            // 유저네임 가져오기
            String userName = json.getString("userName");

            // 세션 정보 만들기
            UserSession userInfo = new UserSession(userName, connection);

            // 채널에 유저 추가
            lobby.addUserSession(userName, userInfo);
            lobby.addConnectionInfo(connection, userName);

            // 로비에 새로운 유저 들어왔다고 브로드 캐스팅
            try {
                Broadcast.broadcastMessage(lobby, MessagePacker.packing(2, ResponseToJson.lobbyUsersToJson()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 새로 들어온 유저에게 로비 정보 브로드 캐스팅
            // Broadcast.broadcastPrivate(connection, json);
        });
    }
}
