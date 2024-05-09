package org.example.businessserver.service;

import org.example.businessserver.object.Channels;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

import java.io.IOException;

public class Broadcast {
    public static Mono<Void> broadcastMessage(Channels.Channel channel, byte[] message) {
        return Flux.fromIterable(channel.getUserSessions().values())
                .flatMap(userSession -> {
                    if (userSession.connection() == null) {
                        return Mono.empty();
                    } else {
                        return userSession.connection().outbound().sendByteArray(Mono.just(message)).then();
                    }
                })
                .then();
    }

    public static Mono<Void> broadcastPrivate(Connection connection, byte[] json) {
        return connection.outbound().sendByteArray(Mono.just(json)).then();
    }
}
