package org.example.businessserver.service;

import org.example.businessserver.lobby.LobbyHandler;
import org.json.JSONException;
import org.json.JSONObject;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.core.buffer.MessageBufferInput;
import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

import java.io.IOException;
import java.util.Arrays;

public class MessageHandler {

    public final LobbyHandler lobbyHandler;

    public MessageHandler(LobbyHandler lobbyHandler) {
        this.lobbyHandler = lobbyHandler;
    }

    public Mono<Void> handleMessage(NettyInbound in, NettyOutbound out) {
        return in.receive()
                .asString()
                .flatMap(request -> {
                    LobbyHandler.initialLogIn(in, request);
                    return Mono.empty();
                }).then();
    }
}
