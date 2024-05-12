package org.example.businessserver.object;

import lombok.Getter;

import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Getter
public class Rooms {
    // 게임 방 목록
    private static final ConcurrentHashMap<Integer, Room> roomsList = new ConcurrentHashMap<>();
    // 사용 가능한 인덱스를 관리할 우선순위 큐
    private static final PriorityQueue<Integer> availableIndexes = new PriorityQueue<>();
    // 게임 방 생성시 부여할 인덱스
    private static Integer index = 0;

    // 생성된 방을 방 목록에 추가
    public static synchronized int addRoom(Room room) {
        int currentIndex;
        if (availableIndexes.isEmpty()) {
            // 사용 가능한 인덱스가 없다면, 현재 인덱스 사용
            currentIndex = ++index;
        } else {
            // 사용 가능한 인덱스가 있다면, 가장 작은 인덱스 재사용
            currentIndex = availableIndexes.poll();
        }

        roomsList.put(currentIndex, room);  // 인덱스를 키, 방을 값으로 리스트에 추가
        return currentIndex;                // 방 번호 리턴
    }

    // 방을 방 목록에서 제거
    public static synchronized void removeRoom(int roomIndex) {
        if (roomsList.containsKey(roomIndex)) {
            roomsList.remove(roomIndex);       // 방 목록에서 방 제거
            availableIndexes.add(roomIndex);   // 제거된 방의 인덱스를 사용 가능 인덱스에 추가
        }
    }

    // 해당 index의 방 반환
    public static Room getRoom(int index) {
        return roomsList.get(index);
    }

    // roomsList를 순회하면서 각 Room의 정보를 문자열 리스트로 반환하는 메소드
    public static List<String> getRoomsInfo() {
        return roomsList.entrySet().stream()
                .map(entry -> "{\"roomIndex\":" + entry.getKey() +
                        ", \"roomName\":\"" + entry.getValue().getRoomName() +
                        "\", \"isOnGame\":" + entry.getValue().getIsOnGame() +
                        ", \"mapName\":\"" + entry.getValue().getMapName() +
                        "\", \"roomUser\":" + entry.getValue().getRoomUserList().size() + "}")
                .collect(Collectors.toList());
    }
}
