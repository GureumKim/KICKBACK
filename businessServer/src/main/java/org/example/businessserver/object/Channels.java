package org.example.businessserver.object;

import lombok.Getter;
import reactor.netty.Connection;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Channels {
    private static final ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap<>();

    // channelName에 해당하는 채널이 있으면 반환, 없으면 create
    public static Channel getOrCreateChannel(String channelName) {
        return channels.computeIfAbsent(channelName, Channel::new);
    }
}
