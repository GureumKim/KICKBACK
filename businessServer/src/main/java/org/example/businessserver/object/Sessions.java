package org.example.businessserver.object;

import reactor.netty.Connection;

import java.util.concurrent.ConcurrentHashMap;

public class Sessions {
    // 전체 유저 관리 목적
    private static ConcurrentHashMap<Connection, Session> sessionList;

    private Sessions() {}

    public static ConcurrentHashMap<Connection, Session> getInstance() {
        if (sessionList == null) {
            sessionList = new ConcurrentHashMap<>();
        }

        return sessionList;
    }
}
