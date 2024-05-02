package org.example.businessserver.object;

import lombok.Getter;
import reactor.netty.Connection;

import java.util.concurrent.ConcurrentHashMap;

public class Channels {
    private static final ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap<>();

    // channelName에 해당하는 채널이 있으면 반환, 없으면 create
    public static Channel getOrCreateChannel(String channelName) {
        return channels.computeIfAbsent(channelName, Channel::new);
    }

    @Getter
    public static class Channel {
        private final ConcurrentHashMap<String, UserSession> userSessions = new ConcurrentHashMap<>();
        private final ConcurrentHashMap<Connection, String> connectionList = new ConcurrentHashMap<>();

        public Channel(String name) {
        }

        public ConcurrentHashMap<String, UserSession> getSessionList() {
            return this.userSessions;
        }

        public UserSession getUserSession(String username) {
            return userSessions.get(username);
        }

        // 채널에 유저 추가
        public void addUserSession(String userName, UserSession userSession) {
            userSessions.put(userName, userSession);
        }

        public void addConnectionInfo(Connection connection, String userName) {
            connectionList.put(connection, userName);
        }

        public String getUserName(Connection connection) {
            return connectionList.get(connection);
        }

        //채널에 있는 유저 제거
        public void removeUserSession(String userName) {
            userSessions.remove(userName);
        }
    }
}
