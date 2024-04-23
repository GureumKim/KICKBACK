package org.example.businessserver.lobby;

import org.example.businessserver.object.ChannelManager;
import org.example.businessserver.object.UserSession;
import org.json.JSONObject;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;
import reactor.netty.NettyInbound;

public class LobbyHandler {

    public final ChannelManager channelManager;

    public LobbyHandler(ChannelManager channelManager) {
        this.channelManager = channelManager;
    }

    public static void initialLogIn(NettyInbound in, String request) {
        in.withConnection(connection -> {
            // byte 배열 string 으로 변경
            String jsonString = new String(request);

            // string -> json 으로 변경
            JSONObject json = null;
            if (jsonString.indexOf("{") == 0) {
                json = new JSONObject(jsonString);
            } else if (jsonString.indexOf("{") == 1) {
                json = new JSONObject(jsonString.substring(1));
            } else {
                json = new JSONObject(jsonString.substring(2));
            }

            // userId 와 message 얻기
            String userName = json.getString("userName");
            String message = json.getString("message");

            System.out.println(userName + ": " + message);

            UserSession sessionInfo = new UserSession(userName, connection);

            broadcastMessage("ssafy", userName, request, sessionInfo).subscribe();

            broadcastPrivate(connection, request).subscribe();
        });
    }


    public static Mono<Void> broadcastMessage(String channelName, String userId ,String message, UserSession sessionInfo) {

        // ChannelManager 통해 주어진 이름의 채널을 가져오거나 존재하지 않을 경우 새로 생성한다.
        ChannelManager.Channel channel = ChannelManager.getOrCreateChannel(channelName);

        channel.addUserSession(userId, sessionInfo);

        // 채널의 사용자 세션 목록을 순회하여 각 사용자 세션에 대해 메시지 전송 작업을 수행한다.
        return Flux.fromIterable(channel.getUserSessions().values())
                // 각 사용자 세션에 대해 비동기적으로 메시지를 전송한다.
                .flatMap(userSession -> {
                    // `connection()` 메서드를 사용하여 사용자 세션의 연결 객체에 접근한 후,
                    // 해당 연결을 통해 문자열 메시지를 비동기적으로 전송한다.
                    return userSession.connection().outbound().sendString(Mono.just(message)).then();
                })
                // 모든 메시지 전송 작업이 완료될 때까지 대기한 후, 완료 신호(Mono<Void>)를 반환한다.
                .then();
    }

    public static Mono<Void> broadcastPrivate(Connection connection, String json) {
        return connection.outbound().sendString(Mono.just(json)).then();
    }
}
