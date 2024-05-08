package org.example.businessserver.service;

import org.example.businessserver.object.Channels;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

import java.io.IOException;

public class Broadcast {
    public static Mono<Void> broadcastMessage(Channels.Channel channel, byte[] message) {
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

    public static Mono<Void> broadcastPrivate(Connection connection, byte[] json) {
        return connection.outbound().sendByteArray(Mono.just(json)).then();
    }

    public static Mono<Void> broadcastLiveServer(Channels.Channel channel, byte[] data) {
        return Flux.fromIterable(channel.getUserSessions().values())
                // 각 사용자 세션에 대해 비동기적으로 메시지를 전송한다.
                .flatMap(userSession -> {
                    // `connection()` 메서드를 사용하여 사용자 세션의 연결 객체에 접근한 후,
                    // 해당 연결을 통해 문자열 메시지를 비동기적으로 전송한다.
                    return userSession.connection().outbound().sendByteArray(Mono.just(data)).then();
                })
                // 모든 메시지 전송 작업이 완료될 때까지 대기한 후, 완료 신호(Mono<Void>)를 반환한다.
                .then();
    }
}
