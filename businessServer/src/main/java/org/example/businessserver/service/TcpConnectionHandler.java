package org.example.businessserver.service;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;

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
//            @Override
//            public void handlerRemoved(ChannelHandlerContext ctx) {
//                // 연결이 끊길 때 userName파싱
//                String userName = Session.getConnectionList().get(conn);
//
//                int channelIdx = Session.getSessionList().get(userName).getSessionState();
//
//                Session.getConnectionList().remove(conn);
//                Session.getSessionList().remove(userName);
//
//                switch (channelIdx) {
//                    case 0:
//                        // 로비 유저 목록에서 세션 제거
//                        ChannelList.getLobby().getSessionList().remove(userName);
//
//                        // 로비에 접속 유저 목록 변동 사항 브로드캐스팅
//                        BroadcastToLobby.broadcastLobby(ToJson.lobbySessionsToJson()).subscribe();
//                        log.info("Client leave [channel]: Lobby / [userName]: " + userName);
//                        break;
//
//                    default:
//                        leaveChannel(userName, channelIdx, SessionState.EXCEPTION);
//
//                        log.info("Client leave [channel]: " + channelIdx + " / [userName]: " + userName);
//                        break;
//                }
//            }

            @Override
            public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {

                log.warn("exception {}", cause.toString());
                ctx.close();
            }
        });
    }
}
