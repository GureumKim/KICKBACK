package org.example.businessserver.object;

import lombok.Getter;

import java.util.concurrent.ConcurrentHashMap;

public class ChannelManager {
    private static final ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap<>();

    // channelName에 해당하는 채널이 있으면 반환, 없으면 create
    public static Channel getOrCreateChannel(String channelName) {
        return channels.computeIfAbsent(channelName, Channel::new);
    }

    @Getter
    public static class Channel {
        private final ConcurrentHashMap<String, UserSession> userSessions = new ConcurrentHashMap<>();

        public Channel(String name) {
        }

        public UserSession getUserSession(String username) {
            return userSessions.get(username);
        }

        // 채널에 유저 추가
        public void addUserSession(String userName, UserSession userSession) {
            userSessions.put(userName, userSession);
        }

        //채널에 있는 유저 제거
        public void removeUserSession(String userName) {
            userSessions.remove(userName);
        }
    }
}
