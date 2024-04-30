package org.example.businessserver.handler;

import org.example.businessserver.config.MsgToJson;
import org.example.businessserver.object.Channels;
import org.example.businessserver.object.UserSession;
import org.json.JSONObject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.NettyInbound;

import java.io.IOException;
import java.util.Objects;

public class LobbyHandler {

    public final Channels channelManager;
    public final MsgToJson msgToJson;

    public LobbyHandler(Channels channelManager, MsgToJson msgToJson) {
        this.channelManager = channelManager;
        this.msgToJson = msgToJson;
    }

    public static void initialLogIn(NettyInbound in, byte[] request) {
        in.withConnection(connection -> {
            // byte 배열 string 으로 변경

            String message = json.getString("message");
            String userName = json.getString("userName");

            if (Objects.equals(message, "handle")) {
                JSONObject data = json.getJSONObject("data");
                broadcastHandle(userName, data).subscribe();
            } else {
                UserSession sessionInfo = new UserSession(userName, connection);
                broadcastMessage("ssafy", userName, request, sessionInfo).subscribe();
            }
        });
    }


    public static Mono<Void> broadcastMessage(String channelName, String userId ,byte[] message, UserSession sessionInfo) {

        // ChannelManager 통해 주어진 이름의 채널을 가져오거나 존재하지 않을 경우 새로 생성한다.
        Channels.Channel channel = Channels.getOrCreateChannel(channelName);

        channel.addUserSession(userId, sessionInfo);

        // 채널의 사용자 세션 목록을 순회하여 각 사용자 세션에 대해 메시지 전송 작업을 수행한다.
        return Flux.fromIterable(channel.getUserSessions().values())
                // 각 사용자 세션에 대해 비동기적으로 메시지를 전송한다.
                .flatMap(userSession -> {
                    // `connection()` 메서드를 사용하여 사용자 세션의 연결 객체에 접근한 후,
                    // 해당 연결을 통해 문자열 메시지를 비동기적으로 전송한다.
                    return userSession.connection().outbound().sendByteArray(Mono.just(message)).then();
                })
                // 모든 메시지 전송 작업이 완료될 때까지 대기한 후, 완료 신호(Mono<Void>)를 반환한다.
                .then();
    }

    public static Mono<Void> broadcastPrivate(Connection connection, byte[] json) throws IOException {
        return connection.outbound().sendByteArray(Mono.just(json)).then();
    }

    public static Mono<Void> broadcastHandle(String userName, JSONObject data){
        Channels.Channel channel = Channels.getOrCreateChannel("ssafy");
        UserSession user = channel.getUserSession(userName);
        // JSONObject를 String으로 변환
        String dataString = data.toString();
        // String 데이터를 byte[]로 변환
        byte[] bytesData = dataString.getBytes();

        return user.connection().outbound().sendByteArray(Mono.just(bytesData)).then();
    }
}
