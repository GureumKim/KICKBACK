package org.example.businessserver.service;

import org.example.businessserver.handler.LobbyHandler;
import org.example.businessserver.message.RequestToJson;
import org.json.JSONObject;
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
                        System.out.println(request);
                        JSONObject json =  RequestToJson.changeMsg(request);
                        System.out.println(json);
                        String channel = json.getString("channel");
                        switch(channel) {
                            case "lobby" :
                                LobbyHandler.initialLogIn(in, json);
                                break;
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
//                    LobbyHandler.initialLogIn(in, request);
                    return Mono.empty();
                }).then();
    }
}
