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
        JSONObject json = new JSONObject(request);

        // userId 와 message 얻기
        String userName = json.getString("userName");
        String message = json.getString("message");

        System.out.println(userName + ": " + message);

        in.withConnection(connection -> {
            System.out.println(connection);
            broadcastPrivate(connection, request).subscribe();
            System.out.println("send");
        });

        System.out.println("complete");
    }

    public static Mono<Void> broadcastPrivate(Connection connection, String json) {
        return connection.outbound().sendString(Mono.just(json)).then();
    }
}
