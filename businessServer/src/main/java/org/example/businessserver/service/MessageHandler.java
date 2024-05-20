package org.example.businessserver.service;

import org.example.businessserver.handler.LobbyHandler;
import org.example.businessserver.message.MessageUnPacker;
import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

import java.io.IOException;

public class MessageHandler {

    public final LobbyHandler lobbyHandler;

    public MessageHandler(LobbyHandler lobbyHandler) {
        this.lobbyHandler = lobbyHandler;
    }

    public Mono<Void> handleMessage(NettyInbound in, NettyOutbound out) {
        return in.receive()
                .asByteArray()
                .flatMap(request -> {
                    try {
                        MessageUnPacker.changeMsg(in, request);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return Mono.empty();
                }).then();
    }
}
