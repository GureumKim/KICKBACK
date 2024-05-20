package org.example.businessserver.service;

import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

public class TcpServerInitializer {
    private static final int PORT = 1370;

    private final TcpConnectionHandler tcpConnectionHandler;
    private final MessageHandler messageHandler;

    public TcpServerInitializer(TcpConnectionHandler tcpConnectionHandler, MessageHandler messageHandler) {
        this.tcpConnectionHandler = tcpConnectionHandler;
        this.messageHandler = messageHandler;
    }

    public Mono<? extends DisposableServer> initializeTcpServer() {
        return TcpServer
                .create() // TcpServer 객체 생성
                .port(PORT) // 포트 설정
                .doOnConnection(tcpConnectionHandler)
                .handle(messageHandler::handleMessage)
                .bind(); // 서버 바인딩
    }
}
