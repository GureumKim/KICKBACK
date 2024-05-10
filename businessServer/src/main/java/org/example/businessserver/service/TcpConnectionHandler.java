package org.example.businessserver.service;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;

import org.example.businessserver.object.Channel;
import org.example.businessserver.object.Channels;
import org.example.businessserver.object.Session;
import org.example.businessserver.object.Sessions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.netty.Connection;

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
            public void handlerRemoved(ChannelHandlerContext ctx) {
                // 1. 마지막 접속 채널 정보 확인
                Session session = Sessions.getInstance().get(conn);
                String channelIdx = session.getChannelIndex();
                Channel channel;

                // 1-1. 로비 확인
                if (channelIdx.equals("lobby")) {
                    channel = Channels.getOrCreateChannel("lobby");
                } else {
                    int idx = Integer.parseInt(channelIdx);
                    channel = Channels.getOrCreateChannel("GameRoom" + idx);
                }

                // 2. 채널에서 유저 제거
                channel.removeUserSession(session.getUserName());

                // 3. 전체 커넥션 목록에서 유저 제거
                Sessions.getInstance().remove(conn);

                // 4. 서버 로그
                log.info("Client leave [channel]: Lobby / [userName]: " + session.getUserName());
            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

                log.warn("exception {}", cause.toString());
                ctx.close();
            }
        });
    }
}
