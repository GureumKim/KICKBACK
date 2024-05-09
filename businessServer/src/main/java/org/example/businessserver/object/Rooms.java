package org.example.businessserver.object;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Getter
public class Rooms {
    // 게임 방 목록
    private static final ConcurrentHashMap<Integer, Room> roomsList = new ConcurrentHashMap<>();
    // 게임 방 생성시 부여할 인덱스
    private static Integer index = 0;

    // 생성된 방을 방 목록에 추가
    public static synchronized int addRoom(Room room) {
        index++;                                // 인덱스값 +1
        roomsList.put(index, room);             // 인덱스를 키, 방을 값으로 리스트에 추가
        return index;                           // 방 번호 리턴
    }

    // 방목록에서 방 제거
    public static synchronized void removeRoom(int index) {
        roomsList.remove(index);
    }

    // 해당 index의 방 반환
    public static Room getRoom(int index) {
        return roomsList.get(index);
    }

    // roomsList를 순회하면서 각 Room의 정보를 문자열 리스트로 반환하는 메소드
    public static List<String> getRoomsInfo() {
        return roomsList.values().stream()
                .map(room -> "{roomName:" + room.getRoomName() + ", isOnGame:" + room.getIsOnGame() + ", mapName:" + room.getMapName() + "}")
                .collect(Collectors.toList());
    }
}
