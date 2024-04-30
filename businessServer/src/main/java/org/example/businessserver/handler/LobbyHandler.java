package org.example.businessserver.handler;

import org.example.businessserver.message.MsgToJson;
import org.example.businessserver.object.Channels;
import org.example.businessserver.object.UserSession;
import org.example.businessserver.service.Broadcast;
import org.json.JSONObject;
import reactor.netty.NettyInbound;

import java.io.IOException;

public class LobbyHandler {

    public final Channels channelManager;

    public LobbyHandler(Channels channelManager) {
        this.channelManager = channelManager;
    }

    public static void initialLogIn(NettyInbound in, byte[] request) {
        in.withConnection(connection -> {
            JSONObject json = null;

            try {
                json = MsgToJson.changeMsg(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // 로비 채널 가져오기
            Channels.Channel channel = Channels.getOrCreateChannel("lobby");
            // 유저네임 가져오기
            String userName = json.getString("userName");
            // 세션 정보 만들기
            UserSession userInfo = new UserSession(userName, connection);
            // 채널에 유저 추가
            channel.addUserSession(userName, userInfo);
            // 로비에 새로운 유저 들어왔다고 브로드 캐스팅
            Broadcast.broadcastMessage(channel, request);
            // 새로 들어온 유저에게 로비 정보 브로드 캐스팅
            Broadcast.broadcastPrivate(connection, request);
        });
    }
}
