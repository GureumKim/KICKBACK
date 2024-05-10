package org.example.businessserver.handler;

import org.example.businessserver.object.Channel;
import org.example.businessserver.object.Channels;
import org.example.businessserver.object.Session;
import org.msgpack.core.MessageUnpacker;
import reactor.netty.NettyInbound;

import java.io.IOException;


public class LiveServerHandler {
    public static void liveServerConnect(NettyInbound in, MessageUnpacker unPacker) {
        in.withConnection(connection -> {
            // 라이브서버 채널 가져오기
            Channel live = Channels.getOrCreateChannel("live");
            // 유저네임 가져오기
            String userName;

            try {
                userName = unPacker.unpackString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // 세션 정보 만들기
            Session session = new Session(connection, userName, userName);

            // 채널에 유저 추가
            live.addUserSession(userName, session);
        });
    }
}
