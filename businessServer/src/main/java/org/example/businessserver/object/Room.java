package org.example.businessserver.object;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Getter
public class Room {
    private static final int MAX_USERS = 6;     // 최대 사용자 수 (방장 포함)
    private final List<String> roomUserList;    // 방 내 사용자 목록
    private final String roomName;              // 방 이름
    private String roomManager;                 // 방장
    private Boolean isOnGame;                   // 게임 중 여부
    private String mapName;                     // 선택된 맵
    private final List<Boolean> isReady;              // 사용자 준비 상태를 저장하는 리스트

    // 게임 방 생성자
    public Room(String roomName, String userName, String mapName) {
        this.roomName = roomName;
        this.roomManager = userName;
        this.mapName = mapName;
        this.isOnGame = false;                  // 초기엔 게임 중이 아님
        this.roomUserList = new ArrayList<>();  // 사용자 목록 초기화
        this.roomUserList.add(userName);        // 방장을 사용자 목록에 추가
        // [false,false,false,false,false,false] 배열 생성
        this.isReady = new ArrayList<>(Collections.nCopies(MAX_USERS, false));
        isReady.set(0, true);                 // 방장은 방 생성 시 자동으로 준비 상태로 설정
    }

    // 게임 시작 메소드
    public void gameStart() {
        isOnGame = true;
    }

    // 사용자 추가 메소드
    public void addUser(String userName) {
        if (roomUserList.size() < MAX_USERS) {
            roomUserList.add(userName);
        }
    }

    // 맵 바꾸는 메소드
    public void changeMapName(String choiceMap) {
        mapName = choiceMap;
    }

    // 사용자의 준비 상태 변경
    public void setUserReady(String userName) {
        int index = roomUserList.indexOf(userName);
        if (index != -1) {
            if (isReady.get(index)) {
                isReady.set(index, false);
            } else {
                isReady.set(index,true);
            }

        }
    }

    // 모든 사용자가 준비되었는지 확인
    public boolean isAllReady() {
        return isReady.stream().allMatch(ready -> ready);
    }

    // 사용자 제거 메소드
    public void removeUser(String userName, int roomIndex) {
        int index = roomUserList.indexOf(userName);
        boolean removed = roomUserList.remove(userName);

        if (removed) {
            isReady.remove(index);                          // 사용자 제거 시 준비 상태도 제거
            if (Objects.equals(userName, roomManager)) {    // 방장이 나간 경우
                if (!roomUserList.isEmpty()) {              // 방장이 나간 후 방에 유저가 남아있는 경우
                    roomManager = roomUserList.get(0);
                } else {                                    // 방장이 나간 후 방에 유저가 없는 경우
                    Rooms.removeRoom(roomIndex);
                }
            }
        }
    }
}

