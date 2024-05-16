package org.example.businessserver.service;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;

import org.example.businessserver.message.ResponseToMsgPack;
import org.example.businessserver.object.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.netty.Connection;

import java.io.IOException;
import java.util.function.Consumer;

public class TcpConnectionHandler implements Consumer<Connection> {

    private static final Logger log = LoggerFactory.getLogger(TcpConnectionHandler.class);

    @Override
    public void accept(Connection conn) {
        conn.addHandler(new LineBasedFrameDecoder(1024));
        conn.addHandler(new ChannelHandlerAdapter() {

            /*
             * 클라이언트 연결 처리 로직
             */
            @Override
            public void handlerAdded(ChannelHandlerContext ctx) {
                // 클라이언트가 연결되었을 때의 처리
                log.info("Client connected: {}", ctx.channel().remoteAddress());
            }

            /*
             * 클라이언트 연결 해제 처리 로직
             */
            @Override
            public void handlerRemoved(ChannelHandlerContext ctx) throws IOException {
                // 1. 마지막 접속 채널 정보 확인
                Session session = Sessions.getInstance().get(conn);
                String channelIdx = session.getChannelIndex();
                Channel channel;

                // 1-1. 로비 확인
                if (channelIdx.equals("lobby")) {
                    channel = Channels.getLobby();

                    // 2-1. 채널에서 유저 제거
                    channel.removeUserSession(session.getUserName());

                    // 2-2. 로비에 있는 유저에게 브로드 캐스팅
                    Broadcast.broadcastMessage(channel, ResponseToMsgPack.lobbyUserToMsgPack(channel)).subscribe();
                } else if (channelIdx.equals("live")) {
                  channel = Channels.getLive();

                  channel.removeUserSession(session.getUserName());
                } else {
                    channel = Channels.getChannel(channelIdx);
                    int idx = Integer.parseInt(channelIdx.substring(11));

                    // 2-1, 유저가 속해 있는 방에서 유저 제거
                    channel.removeUser(session.getUserName(), idx);

                    // 2-2. 채널에서 유저 제거
                    channel.removeUserSession(session.getUserName());

                    // 2-3. 방에 있는 유저에게 브로드 캐스팅
                    Broadcast.broadcastMessage(channel, ResponseToMsgPack.gameChannelInfoToMsgPack(idx)).subscribe();
                }

                // 3. 전체 커넥션 목록에서 유저 제거
                Sessions.getInstance().remove(conn);

                // 4. 서버 로그
                log.info("Client leave [channel]:" + channelIdx + "/ [userName]: " + session.getUserName());
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

                log.warn("exception {}", cause.toString());
                ctx.close();
            }
        });
    }
}
