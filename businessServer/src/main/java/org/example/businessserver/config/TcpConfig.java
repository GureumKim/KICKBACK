package org.example.businessserver.config;

import org.example.businessserver.handler.LobbyHandler;
import org.example.businessserver.object.Channels;
import org.example.businessserver.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TcpConfig {
    @Bean
    public TcpServerRunner tcpServerConfigTest() {
        return new TcpServerRunner(tcpServerInitializer());
    }

    @Bean
    public Channels channelManager() { return new Channels(); }

    @Bean
    public LobbyHandler lobbyHandler() { return new LobbyHandler(); }

    @Bean
    public MessageHandler messageHandler() { return new MessageHandler(lobbyHandler()); }

    @Bean
    public TcpServerInitializer tcpServerInitializer() {
        return new TcpServerInitializer(tcpConnectionHandler(),messageHandler());
    }

    @Bean
    public TcpConnectionHandler tcpConnectionHandler() {
        return new TcpConnectionHandler();
    }
}
