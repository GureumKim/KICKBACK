package org.example.businessserver.service;

import org.example.businessserver.lobby.LobbyHandler;
import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

public class MessageHandler {

    public final LobbyHandler lobbyHandler;

    public MessageHandler(LobbyHandler lobbyHandler) {
        this.lobbyHandler = lobbyHandler;
    }

    public Mono<Void> handleMessage(NettyInbound in, NettyOutbound out) {
        return in.receive()
                .asByteArray()
                .flatMap(request -> {
                    LobbyHandler.initialLogIn(in, request);
                    return Mono.empty();
                }).then();
    }
}
