package org.example.businessserver.service;

import org.example.businessserver.object.Channel;
import org.example.businessserver.object.Sessions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

import java.util.Arrays;

public class Broadcast {
    public static Mono<Void> broadcastMessage(Channel channel, byte[] message) {
        return Flux.fromIterable(channel.getSessionsInfo())
                .flatMap(session -> {
                    if (Sessions.getInstance().get(session.getConn()) == null) {
                        return Mono.empty();
                    } else {
                        return session.getConn().outbound().sendByteArray(Mono.just(message)).then();
                    }
                })
                .then();
    }

    public static Mono<Void> broadcastPrivate(Connection connection, byte[] json) {
        return connection.outbound().sendByteArray(Mono.just(json)).then();
    }
}
