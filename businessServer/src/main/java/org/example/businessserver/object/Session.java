package org.example.businessserver.object;

import reactor.netty.Connection;

public class Session {
    private final Connection conn;
    private final String userName;
    private String channelIndex;

    public Session(Connection conn, String userName, String channelIndex) {
        this.conn = conn;
        this.userName = userName;
        this.channelIndex = channelIndex;
    }

    public void setChannelIndex(String channelIndex) {
        this.channelIndex = channelIndex;
    }

    public Connection getConn() {
        return conn;
    }

    public String getUserName() {
        return userName;
    }

    public String getChannelIndex() {
        return channelIndex;
    }
}
