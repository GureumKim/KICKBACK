package org.example.businessserver.service;

import org.example.businessserver.object.Channel;
import org.example.businessserver.object.Channels;
import org.example.businessserver.object.Sessions;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.Connection;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Broadcast {
    public static Mono<Void> broadcastMessage(Channel channel, byte[] message) {
        return Flux.fromIterable(channel.getSessionsInfo())
                .flatMap(session -> {
                    if (Sessions.getInstance().get(session.getConn()) == null) {
                        return Mono.empty();
                    } else {
                        // 메시지 길이를 먼저 보내기
                        byte[] length = ByteBuffer.allocate(4).putInt(message.length).order(ByteOrder.BIG_ENDIAN).array();
                        Mono<Void> sendLength = session.getConn().outbound().sendByteArray(Mono.just(length)).then();
                        // 실제 메시지 데이터 보내기
                        Mono<Void> sendMessage = session.getConn().outbound().sendByteArray(Mono.just(message)).then();

                        // 길이를 먼저 보내고, 길이 전송이 완료된 후 메시지 데이터 보내기
                        return sendLength.then(sendMessage);
                    }
                })
                .then();
    }

    public static Mono<Void> broadcastPrivate(Connection connection, byte[] message) {
        // 메시지 길이를 먼저 보내기
        byte[] length = ByteBuffer.allocate(4).putInt(message.length).order(ByteOrder.BIG_ENDIAN).array();
        Mono<Void> sendLength = connection.outbound().sendByteArray(Mono.just(length)).then();
        // 실제 메시지 데이터 보내기
        Mono<Void> sendMessage = connection.outbound().sendByteArray(Mono.just(message)).then();

        // 길이를 먼저 보내고, 길이 전송이 완료된 후 메시지 데이터 보내기
        return sendLength.then(sendMessage);
    }

    public static Mono<Void> broadcastLiveServer(byte[] message) {
        Connection connection = Channels.getLive().getUserSession("LiveServer").getConn();
        return connection.outbound().sendByteArray(Mono.just(message)).then();
    }
}
