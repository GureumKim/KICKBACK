package org.example.businessserver.handler;

import org.example.businessserver.message.BusinessToLive;
import org.example.businessserver.message.ResponseToMsgPack;
import org.example.businessserver.object.*;
import org.example.businessserver.service.Broadcast;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;
import java.util.List;

public class ChannelHandler {
    public static void createChannel(MessageUnpacker unpacker) throws IOException {
        String userName = unpacker.unpackString(); // 채널 생성자
        String ChannelName = unpacker.unpackString(); // 채널 이름
        String mapName = unpacker.unpackString(); // 맵 이름
        String gameMode = unpacker.unpackString(); // 게임 모드

        // 검사할 문자들을 배열로 정의
        char[] specialChars = {'"', '}', ',', '\'', '\\', '[', ']', ':'};

        // ChannelName에서 특수 문자 제거
        for (char ch : specialChars) {
            ChannelName = ChannelName.replace(String.valueOf(ch), "");
        }

        Channel channel = new Channel(ChannelName, userName, mapName, gameMode); // 새로운 채널 생성
        int channelIdx = Channels.addChannel(channel);             // 채널 리스트에 추가 후 채널 번호 리턴

        System.out.println("생성 완료: " + channelIdx + "번 채널 - " + ChannelName);

        // 로비 채널 가져오기
        Channel lobby = Channels.getLobby();
        // 유저세션 가져오기
        Session session = lobby.getUserSession(userName);
        // 로비에서 유저세션 제거
        lobby.removeUserSession(userName);
        // 새로운 채널에 유저세션 추가
        channel.addUserSession(userName, session);
        // 세션 정보 업데이트
        session.setChannelIndex("GameChannel" + channelIdx);

        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyUserToMsgPack(lobby)).subscribe();
        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyChannelToMsgPack()).subscribe();
        Broadcast.broadcastMessage(channel, ResponseToMsgPack.gameChannelInfoToMsgPack(channelIdx)).subscribe();
    }

    public static void joinChannel(MessageUnpacker unpacker) throws IOException {
        String userName = unpacker.unpackString();
        int channelIdx = unpacker.unpackInt();

        // 들어가려는 채널 가져오기
        Channel channel = Channels.getChannel("GameChannel" + channelIdx);
        // 로비 채널 가져오기
        Channel lobby = Channels.getLobby();
        // 세션 가져오기
        Session session = lobby.getUserSession(userName);
        // 로비에서 유저세션 제거
        lobby.removeUserSession(userName);
        // 들어가려는 채널에 유저세션 추가
        channel.addUserSession(userName, session);
        // 세션 정보 업데이트
        session.setChannelIndex("GameChannel" + channelIdx);
        // 들어가려는 채널에 유저 추가
        channel.addUser(userName);
        // 들어간 유저 레디상태 바꾸기
        channel.setUserReady(userName);

        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyUserToMsgPack(lobby)).subscribe();
        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyChannelToMsgPack()).subscribe();
        Broadcast.broadcastMessage(channel, ResponseToMsgPack.gameChannelInfoToMsgPack(channelIdx)).subscribe();
    }

    public static void leaveChannel(MessageUnpacker unpacker) throws IOException {
        String userName = unpacker.unpackString();
        int channelIdx = unpacker.unpackInt();

        // 나가려는 채널 가져오기
        Channel channel = Channels.getChannel("GameChannel" + channelIdx);
        // 유저세션 가져오기
        Session session = channel.getUserSession(userName);
        // 채널 에서 유저세션 제거
        channel.removeUserSession(userName);
        // 나가려는 채널 유저 제거
        channel.removeUser(userName, channelIdx);
        // 로비 채널 가져오기
        Channel lobby = Channels.getLobby();
        // 로비 채널에 유저세션 추가
        lobby.addUserSession(userName, session);
        // 세션 정보 업데이트
        session.setChannelIndex("lobby");

        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyUserToMsgPack(lobby)).subscribe();
        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyChannelToMsgPack()).subscribe();

        if (Channels.getChannel("GameChannel" + channelIdx) != null) {
            Broadcast.broadcastMessage(channel, ResponseToMsgPack.gameChannelInfoToMsgPack(channelIdx)).subscribe();
        }
    }

    public static void readyUser(MessageUnpacker unpacker) throws IOException {
        String userName = unpacker.unpackString();
        int channelIdx = unpacker.unpackInt();

        // 채널 가져오기
        Channel channel = Channels.getChannel("GameChannel" + channelIdx);
        // 준비 상태 바꾸기
        channel.setUserReady(userName);

        Broadcast.broadcastMessage(channel, ResponseToMsgPack.gameChannelInfoToMsgPack(channelIdx)).subscribe();
    }

    public static void startGame(MessageUnpacker unpacker) throws IOException {
        int channelIdx = unpacker.unpackInt();
        String gameMode = unpacker.unpackString();

        // 로비 채널 가져오기
        Channel lobby = Channels.getLobby();
        // 채널 가져오기
        Channel channel = Channels.getChannel("GameChannel" + channelIdx);

        if (gameMode.equals("soccer")) {
            List<Integer> team =  channel.getTeamColor();
            long countOfZero = team.stream() // 스트림 생성
                    .filter(color -> color == 0) // 0인 요소만 필터링
                    .count(); // 필터링된 요소들의 개수를 센다

            if (channel.getChannelUserList().size()%2 == 0 && countOfZero != channel.getChannelUserList().size()/2) {
                Broadcast.broadcastMessage(channel, ResponseToMsgPack.errorToMsgPack("팀 밸런스가 맞지 않습니다! 팀을 변경해주세요.")).subscribe();
            } else {
                // 모두 레디 상태인지 확인
                if (channel.isAllReady()) {
                    // 게임 중으로 변경
                    channel.gameStart();
                    Broadcast.broadcastLiveServer(BusinessToLive.packing(6, channelIdx)).subscribe();
                    Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyChannelToMsgPack()).subscribe();
                } else {
                    Broadcast.broadcastMessage(channel, ResponseToMsgPack.errorToMsgPack("모든 유저가 준비되지 않았습니다!")).subscribe();
                }
            }
        }
    }

    public static void changeMap(MessageUnpacker unpacker) throws IOException {
        String mapName = unpacker.unpackString();
        int channelIdx = unpacker.unpackInt();

        // 로비 채널 가져오기
        Channel lobby = Channels.getLobby();
        // 채널 가져오기
        Channel channel = Channels.getChannel("GameChannel" + channelIdx);
        // 맵 변경
        channel.changeMapName(mapName);

        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyChannelToMsgPack()).subscribe();
        Broadcast.broadcastMessage(channel, ResponseToMsgPack.gameChannelInfoToMsgPack(channelIdx)).subscribe();
    }

    public static void endGame(MessageUnpacker unpacker) throws IOException {
        int channelIdx = unpacker.unpackInt();
        Broadcast.broadcastLiveServer(BusinessToLive.packing(8, channelIdx)).subscribe();
    }

    // 축구 모드
    public static void teamChange(MessageUnpacker unpacker) throws IOException {
        int channelIndex = unpacker.unpackInt();
        String userName = unpacker.unpackString();

        // 채널 가져오기
        Channel channel = Channels.getChannel("GameChannel" + channelIndex);
        // 유저 팀 컬러 바꾸기
        channel.setTeamColor(userName);

        Broadcast.broadcastMessage(channel, ResponseToMsgPack.gameChannelInfoToMsgPack(channelIndex)).subscribe();
    }

    // 캐릭터 바꾸기
    public static void charChange(MessageUnpacker unpacker) throws IOException {
        int channelIndex = unpacker.unpackInt();
        String userName = unpacker.unpackString();
        int characterIndex = unpacker.unpackInt();

        // 채널 가져오기
        Channel channel = Channels.getChannel("GameChannel" + channelIndex);
        // 캐릭터 바꾸기
        channel.setCharacter(userName, characterIndex);

        Broadcast.broadcastMessage(channel, ResponseToMsgPack.gameChannelInfoToMsgPack(channelIndex)).subscribe();
    }
}
