package org.example.businessserver.service;

import org.example.businessserver.message.MessagePacker;
import org.example.businessserver.message.Type;
import org.example.businessserver.object.Message;
import org.msgpack.core.MessagePack;
import org.msgpack.core.MessageUnpacker;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.NettyInbound;
import reactor.netty.NettyOutbound;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class MessageHandler {

    public Mono<Void> handleMessage(NettyInbound in, NettyOutbound out) {
        return in.receive()
                .asByteArray() // 데이터를 바이트 배열로 받음
                .flatMap(request -> {


                    System.out.println(Arrays.toString(request));

                    try {
                        out.sendByteArray(Mono.just(MessagePacker.packing(Type.TEST))).then().subscribe();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    return Mono.empty(); // 여기에서 처리를 단순히 완료함
                }).then();
    }
}
