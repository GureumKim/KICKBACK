package org.example.businessserver.service;

import io.netty.util.CharsetUtil;
import org.example.businessserver.message.MessagePacker;
import org.example.businessserver.message.MessageUnPacker;
import reactor.core.publisher.Mono;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageHandler {

    public Mono<Void> handleMessage(NettyInbound in, NettyOutbound out) {
        return in.receive()
                .asString()
                .flatMap(request -> {
                    System.out.println(request);

//                    try {
//                        MessageUnPacker.unpacking(request);
//                    } catch (IOException e) {
//                        return reactor.core.publisher.Flux.error(new RuntimeException(e));
//                    }

                    try {
//                        out.sendByteArray(Mono.just(MessagePacker.packing("한국어 테스트"))).then().subscribe();
//                        out.sendByteArray(Mono.just(MessagePacker.packing("english test"))).then().subscribe();
//                        out.sendByteArray(Mono.just(MessagePacker.packing("command: create"))).then().subscribe();
//                        out.sendByteArray(Mono.just(MessagePacker.packing("command: create"))).then().subscribe();
                        out.sendByteArray(Mono.just(MessagePacker.packing("command: create"))).then().subscribe();
//                        out.sendByteArray(Mono.just(MessagePacker.packing("test"))).then().subscribe();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    return Mono.empty();
                }).then();
    }
}
