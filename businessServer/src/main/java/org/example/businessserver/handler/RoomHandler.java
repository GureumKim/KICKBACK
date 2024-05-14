package org.example.businessserver.handler;

import org.example.businessserver.message.BusinessToLive;
import org.example.businessserver.message.MessageUnPacker;
import org.example.businessserver.message.ResponseToMsgPack;
import org.example.businessserver.object.*;
import org.example.businessserver.service.Broadcast;
import org.msgpack.core.MessageUnpacker;

import java.io.IOException;

public class RoomHandler {
    public static void createRoom(MessageUnpacker unpacker) throws IOException {
        String userName = unpacker.unpackString(); // 방 생성자
        String roomName = unpacker.unpackString(); // 방 이름
        String mapName = unpacker.unpackString(); // 맵 이름
        String gameMode = unpacker.unpackString(); // 게임 모드

        Room newGameRoom = new Room(roomName, userName, mapName, gameMode); // 새로운 방 생성
        int roomIdx = Rooms.addRoom(newGameRoom);               // 방 리스트에 추가 후 방 번호 리턴

        System.out.println("생성 완료: " + roomIdx + "번방 - " + roomName);

        // 로비 채널 가져오기
        Channel lobby = Channels.getOrCreateChannel("lobby");
        // 유저세션 가져오기
        Session session = lobby.getUserSession(userName);
        // 로비에서 유저세션 제거
        lobby.removeUserSession(userName);
        // 새로운 방 채널 생성
        Channel myGameRoom = Channels.getOrCreateChannel("GameRoom" + roomIdx);
        // 새로운 방 채널에 유저세션 추가
        myGameRoom.addUserSession(userName, session);
        // 세션 정보 업데이트
        session.setChannelIndex(Integer.toString(roomIdx));

        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyUserToMsgPack(lobby)).subscribe();
        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyRoomToMsgPack()).subscribe();
        Broadcast.broadcastMessage(myGameRoom, ResponseToMsgPack.gameRoomInfoToMsgPack(roomIdx)).subscribe();
    }

    public static void joinRoom(MessageUnpacker unpacker) throws IOException {
        String userName = unpacker.unpackString();
        int roomIdx = unpacker.unpackInt();

        // 들어가려는 방 가져오기
        Channel wantRoom = Channels.getOrCreateChannel("GameRoom" + roomIdx);
        // 로비 채널 가져오기
        Channel lobby = Channels.getOrCreateChannel("lobby");
        // 세션 가져오기
        Session session = lobby.getUserSession(userName);
        // 로비에서 유저세션 제거
        lobby.removeUserSession(userName);
        // 들어가려는 방 채널에 유저세션 추가
        wantRoom.addUserSession(userName, session);
        // 세션 정보 업데이트
        session.setChannelIndex(Integer.toString(roomIdx));

        // 들어가려는 방 가져오기
        Room room = Rooms.getRoom(roomIdx);
        // 들어가려는 방에 유저 추가
        room.addUser(userName);
        // 들어간 유저 레디상태 바꾸기
        room.setUserReady(userName);

        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyUserToMsgPack(lobby)).subscribe();
        Broadcast.broadcastMessage(wantRoom, ResponseToMsgPack.gameRoomInfoToMsgPack(roomIdx)).subscribe();
    }

    public static void leaveRoom(MessageUnpacker unpacker) throws IOException {
        String userName = unpacker.unpackString();
        int roomIdx = unpacker.unpackInt();

        // 나가려는 방 가져오기
        Room room = Rooms.getRoom(roomIdx);
        // 나가려는 방 유저 제거
        room.removeUser(userName, roomIdx);
        // 로비 채널 가져오기
        Channel lobby = Channels.getOrCreateChannel("lobby");
        // 나가려는 방 채널 가져오기
        Channel outRoom = Channels.getOrCreateChannel("GameRoom" + roomIdx);
        // 유저세션 가져오기
        Session session = outRoom.getUserSession(userName);
        // 방 채널 에서 유저세션 제거
        outRoom.removeUserSession(userName);
        // 로비 채널에 유저세션 추가
        lobby.addUserSession(userName, session);
        // 세션 정보 업데이트
        session.setChannelIndex("lobby");

        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyUserToMsgPack(lobby)).subscribe();
        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyRoomToMsgPack()).subscribe();
        Broadcast.broadcastMessage(outRoom, ResponseToMsgPack.gameRoomInfoToMsgPack(roomIdx)).subscribe();
    }

    public static void readyUser(MessageUnpacker unpacker) throws IOException {
        String userName = unpacker.unpackString();
        int roomIdx = unpacker.unpackInt();

        // 방 채널 가져오기
        Channel cRoom = Channels.getOrCreateChannel("GameRoom" + roomIdx);
        // 방 가져오기
        Room room = Rooms.getRoom(roomIdx);
        // 준비 상태 바꾸기
        room.setUserReady(userName);

        Broadcast.broadcastMessage(cRoom, ResponseToMsgPack.gameRoomInfoToMsgPack(roomIdx)).subscribe();
    }

    public static void startGame(MessageUnpacker unpacker) throws IOException {
        int roomIdx = unpacker.unpackInt();
        System.out.println(roomIdx);
        // 라이브서버 채널 가져오기
        Channel live = Channels.getOrCreateChannel("live");

        ////////////////////////////////////////////////////////////////////1
        // 로비 채널 가져오기
        Channel lobby = Channels.getOrCreateChannel("lobby");
        // 방 채널 가져오기
        Channel cRoom = Channels.getOrCreateChannel("GameRoom" + roomIdx);
        // 방 가져오기
        Room room = Rooms.getRoom(roomIdx);
        ////////////////////////////////////////////////////////////////////1

        Broadcast.broadcastLiveServer(BusinessToLive.packing(6, roomIdx)).subscribe();
        System.out.println("성공");

        ////////////////////////////////////////////////////////////////////2
        // 모두 레디 상태인지 확인
        if (room.isAllReady()) {
            // 게임 중으로 변경
            room.gameStart();
            Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyRoomToMsgPack()).subscribe();
        } else {
            Broadcast.broadcastMessage(cRoom, ResponseToMsgPack.errorToMsgPack("모든 유저가 준비되지 않았습니다!")).subscribe();
        }
        ////////////////////////////////////////////////////////////////////2
    }

    public static void changeMap(MessageUnpacker unpacker) throws IOException {
        String mapName = unpacker.unpackString();
        int roomIdx = unpacker.unpackInt();

        // 로비 채널 가져오기
        Channel lobby = Channels.getOrCreateChannel("lobby");
        // 방 채널 가져오기
        Channel cRoom = Channels.getOrCreateChannel("GameRoom" + roomIdx);
        // 방 가져오기
        Room room = Rooms.getRoom(roomIdx);
        // 맵 변경
        room.changeMapName(mapName);

        Broadcast.broadcastMessage(lobby, ResponseToMsgPack.lobbyRoomToMsgPack()).subscribe();
        Broadcast.broadcastMessage(cRoom, ResponseToMsgPack.gameRoomInfoToMsgPack(roomIdx)).subscribe();
    }

    public static void endGame(MessageUnpacker unpacker) throws IOException {
        int roomIdx = unpacker.unpackInt();

        // 라이브서버 채널 가져오기
        Channel live = Channels.getOrCreateChannel("live");

        Broadcast.broadcastLiveServer(BusinessToLive.packing(8, roomIdx)).subscribe();
    }

    // 축구 모드
    public static void teamChange(MessageUnpacker unpacker) throws IOException {
        int roomIndex = unpacker.unpackInt();
        String userName = unpacker.unpackString();

        // 방 채널 가져오기
        Channel cRoom = Channels.getOrCreateChannel("GameRoom" + roomIndex);
        // 방 가져오기
        Room room = Rooms.getRoom(roomIndex);
        // 유저 팀 컬러 바꾸기
        room.setTeamColor(userName);

        Broadcast.broadcastMessage(cRoom, ResponseToMsgPack.gameRoomInfoToMsgPack(roomIndex)).subscribe();
    }
}
