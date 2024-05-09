package org.example.businessserver.object;

import lombok.Getter;
import reactor.netty.Connection;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Channels {
    private static final ConcurrentHashMap<String, Channel> channels = new ConcurrentHashMap<>();

    @Getter
    private static final ConcurrentHashMap<Connection, String> connectionList = new ConcurrentHashMap<>();

    // channelName에 해당하는 채널이 있으면 반환, 없으면 create
    public static Channel getOrCreateChannel(String channelName) {
        return channels.computeIfAbsent(channelName, Channel::new);
    }

    public static void addConnectionInfo(Connection connection, String userName) {
        connectionList.put(connection, userName);
    }

    public static String getUserName(Connection connection) {
        return connectionList.get(connection);
    }

    @Getter
    public static class Channel {
        private final ConcurrentHashMap<String, UserSession> userSessions = new ConcurrentHashMap<>();

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
        //채널에 있는 유저 제거
        public void removeUserSession(String userName) {
            userSessions.remove(userName);
        }

        // userSessions의 모든 키 값을 새로운 배열에 담아 반환하는 메소드
        public Set<String> getSessionKeys() {
            return new HashSet<>(this.userSessions.keySet());
        }
    }
}
