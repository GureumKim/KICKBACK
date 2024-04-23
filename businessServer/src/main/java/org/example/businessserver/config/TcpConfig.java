package org.example.businessserver.config;

import org.example.businessserver.lobby.LobbyHandler;
import org.example.businessserver.object.ChannelManager;
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
    public ChannelManager channelManager() { return new ChannelManager(); }

    @Bean
    public LobbyHandler lobbyHandler() { return new LobbyHandler(channelManager()); }

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
