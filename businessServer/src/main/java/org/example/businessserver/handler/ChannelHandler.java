package org.example.businessserver.handler;

import org.example.businessserver.message.BusinessToLive;
import org.example.businessserver.message.ResponseToMsgPack;
import org.example.businessserver.object.*;
import org.example.businessserver.service.Broadcast;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;

public class ChannelHandler {
    public static void createChannel(MessageUnpacker unpacker) throws IOException {
        String userName = unpacker.unpackString(); // 방 생성자
        String ChannelName = unpacker.unpackString(); // 방 이름
        String mapName = unpacker.unpackString(); // 맵 이름
        String gameMode = unpacker.unpackString(); // 게임 모드

        Channel newGameChannel = new Channel(ChannelName, userName, mapName, gameMode); // 새로운 방 생성
        int ChannelIdx = Channels.addChannel(newGameChannel);               // 방 리스트에 추가 후 방 번호 리턴

        System.out.println("생성 완료: " + ChannelIdx + "번방 - " + ChannelName);

        // 로비 채널 가져오기
        a lobby = b.getOrCreateChannel("lobby");
        // 유저세션 가져오기
        Session session = lobby.getUserSession(userName);
        // 로비에서 유저세션 제거
        lobby.removeUserSession(userName);
        // 새로운 방 채널 생성
        a myGameChannel = b.getOrCreateChannel("GameChannel" + ChannelIdx);
        // 새로운 방 채널에 유저세션 추가
        myGameChannel.addUserSession(userName, session);
        // 세션 정보 업데이트
        session.setChannelIndex(Integer.toString(ChannelIdx));

        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyUserToMsgPack(lobby)).subscribe();
        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyChannelToMsgPack()).subscribe();
        Broadcast.broadcastMessage(myGameChannel, ResponseToMsgPack.gameChannelInfoToMsgPack(ChannelIdx)).subscribe();
    }

    public static void joinChannel(MessageUnpacker unpacker) throws IOException {
        String userName = unpacker.unpackString();
        int ChannelIdx = unpacker.unpackInt();

        // 들어가려는 방 가져오기
        a wantChannel = b.getOrCreateChannel("GameChannel" + ChannelIdx);
        // 로비 채널 가져오기
        a lobby = b.getOrCreateChannel("lobby");
        // 세션 가져오기
        Session session = lobby.getUserSession(userName);
        // 로비에서 유저세션 제거
        lobby.removeUserSession(userName);
        // 들어가려는 방 채널에 유저세션 추가
        wantChannel.addUserSession(userName, session);
        // 세션 정보 업데이트
        session.setChannelIndex(Integer.toString(ChannelIdx));

        // 들어가려는 방 가져오기
        Channel Channel = Channels.getChannel(ChannelIdx);
        // 들어가려는 방에 유저 추가
        Channel.addUser(userName);
        // 들어간 유저 레디상태 바꾸기
        Channel.setUserReady(userName);

        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyUserToMsgPack(lobby)).subscribe();
        Broadcast.broadcastMessage(wantChannel, ResponseToMsgPack.gameChannelInfoToMsgPack(ChannelIdx)).subscribe();
    }

    public static void leaveChannel(MessageUnpacker unpacker) throws IOException {
        String userName = unpacker.unpackString();
        int ChannelIdx = unpacker.unpackInt();

        // 나가려는 방 가져오기
        Channel Channel = Channels.getChannel(ChannelIdx);
        // 나가려는 방 유저 제거
        Channel.removeUser(userName, ChannelIdx);
        // 로비 채널 가져오기
        a lobby = b.getOrCreateChannel("lobby");
        // 나가려는 방 채널 가져오기
        a outChannel = b.getOrCreateChannel("GameChannel" + ChannelIdx);
        // 유저세션 가져오기
        Session session = outChannel.getUserSession(userName);
        // 방 채널 에서 유저세션 제거
        outChannel.removeUserSession(userName);
        // 로비 채널에 유저세션 추가
        lobby.addUserSession(userName, session);
        // 세션 정보 업데이트
        session.setChannelIndex("lobby");

        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyUserToMsgPack(lobby)).subscribe();
        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyChannelToMsgPack()).subscribe();
        Broadcast.broadcastMessage(outChannel, ResponseToMsgPack.gameChannelInfoToMsgPack(ChannelIdx)).subscribe();
    }

    public static void readyUser(MessageUnpacker unpacker) throws IOException {
        String userName = unpacker.unpackString();
        int ChannelIdx = unpacker.unpackInt();

        // 방 채널 가져오기
        a cChannel = b.getOrCreateChannel("GameChannel" + ChannelIdx);
        // 방 가져오기
        Channel Channel = Channels.getChannel(ChannelIdx);
        // 준비 상태 바꾸기
        Channel.setUserReady(userName);

        Broadcast.broadcastMessage(cChannel, ResponseToMsgPack.gameChannelInfoToMsgPack(ChannelIdx)).subscribe();
    }

    public static void startGame(MessageUnpacker unpacker) throws IOException {
        int ChannelIdx = unpacker.unpackInt();
        System.out.println(ChannelIdx);
        // 라이브서버 채널 가져오기
        a live = b.getOrCreateChannel("live");

        ////////////////////////////////////////////////////////////////////1
        // 로비 채널 가져오기
        a lobby = b.getOrCreateChannel("lobby");
        // 방 채널 가져오기
        a cChannel = b.getOrCreateChannel("GameChannel" + ChannelIdx);
        // 방 가져오기
        Channel Channel = Channels.getChannel(ChannelIdx);
        ////////////////////////////////////////////////////////////////////1

        Broadcast.broadcastLiveServer(BusinessToLive.packing(6, ChannelIdx)).subscribe();
        System.out.println("성공");

        ////////////////////////////////////////////////////////////////////2
        // 모두 레디 상태인지 확인
        if (Channel.isAllReady()) {
            // 게임 중으로 변경
            Channel.gameStart();
            Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyChannelToMsgPack()).subscribe();
        } else {
            Broadcast.broadcastMessage(cChannel, ResponseToMsgPack.errorToMsgPack("모든 유저가 준비되지 않았습니다!")).subscribe();
        }
        ////////////////////////////////////////////////////////////////////2
    }

    public static void changeMap(MessageUnpacker unpacker) throws IOException {
        String mapName = unpacker.unpackString();
        int ChannelIdx = unpacker.unpackInt();

        // 로비 채널 가져오기
        a lobby = b.getOrCreateChannel("lobby");
        // 방 채널 가져오기
        a cChannel = b.getOrCreateChannel("GameChannel" + ChannelIdx);
        // 방 가져오기
        Channel Channel = Channels.getChannel(ChannelIdx);
        // 맵 변경
        Channel.changeMapName(mapName);

        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyChannelToMsgPack()).subscribe();
        Broadcast.broadcastMessage(cChannel, ResponseToMsgPack.gameChannelInfoToMsgPack(ChannelIdx)).subscribe();
    }

    public static void endGame(MessageUnpacker unpacker) throws IOException {
        int ChannelIdx = unpacker.unpackInt();

        // 라이브서버 채널 가져오기
        a live = b.getOrCreateChannel("live");

        Broadcast.broadcastLiveServer(BusinessToLive.packing(8, ChannelIdx)).subscribe();
    }

    // 축구 모드
    public static void teamChange(MessageUnpacker unpacker) throws IOException {
        int ChannelIndex = unpacker.unpackInt();
        String userName = unpacker.unpackString();

        // 방 채널 가져오기
        a cChannel = b.getOrCreateChannel("GameChannel" + ChannelIndex);
        // 방 가져오기
        Channel Channel = Channels.getChannel(ChannelIndex);
        // 유저 팀 컬러 바꾸기
        Channel.setTeamColor(userName);

        Broadcast.broadcastMessage(cChannel, ResponseToMsgPack.gameChannelInfoToMsgPack(ChannelIndex)).subscribe();
    }
}
