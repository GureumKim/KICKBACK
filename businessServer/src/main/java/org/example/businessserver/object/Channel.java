package org.example.businessserver.object;

import org.example.businessserver.message.ResponseToMsgPack;
import org.example.businessserver.service.Broadcast;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Channel {
    private String ChannelName;
    private final ConcurrentHashMap<String, Session> userSessions = new ConcurrentHashMap<>();

    public Channel(String name) {
        this.ChannelName = name;
    }

    public Session getUserSession(String username) {
        return userSessions.get(username);
    }

    // 채널에 유저 추가
    public void addUserSession(String userName, Session session) {
        userSessions.put(userName, session);
    }

    //채널에 있는 유저 제거
    public void removeUserSession(String userName) {
        userSessions.remove(userName);
    }

    // userSessions의 모든 키 값을 새로운 배열에 담아 반환하는 메소드
    public Set<String> getSessionsName() {
        return new HashSet<>(this.userSessions.keySet());
    }

    // 참여자 정보 조회
    public Set<Session> getSessionsInfo() {
        return new HashSet<>(this.userSessions.values());
    }
}
