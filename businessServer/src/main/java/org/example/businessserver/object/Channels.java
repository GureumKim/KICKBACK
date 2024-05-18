package org.example.businessserver.object;

import lombok.Getter;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;

@Getter
public class Channels {
    // 게임 채널 목록
    private static final ConcurrentHashMap<String, Channel> channelsList = new ConcurrentHashMap<>();
    // 사용 가능한 인덱스를 관리할 우선순위 큐
    private static final PriorityBlockingQueue<Integer> availableIndexes = new PriorityBlockingQueue<>();
    // 게임 채널 생성시 부여할 인덱스
    private static Integer index = 0;

    // LOBBY 채널 생성
    public static final Channel lobby = new Channel("lobby");

    // LOBBY 채널 반환
    public static Channel getLobby() {
        return lobby;
    }

    // LIVE 채널 생성
    public static final Channel live = new Channel("live");

    // LIVE 채널 반환
    public static Channel getLive() {
        return live;
    }

    // 생성된 채널을 채널 목록에 추가
    public static synchronized int addChannel(Channel channel) {
        int currentIndex;

        if (availableIndexes.isEmpty()) {
            // 사용 가능한 인덱스가 없다면, 현재 인덱스 사용
            currentIndex = ++index;
        } else {
            // 사용 가능한 인덱스가 있다면, 가장 작은 인덱스 재사용
            currentIndex = availableIndexes.poll();
        }

        channelsList.put("GameChannel" + currentIndex, channel);  // 인덱스를 키, 채널을 값으로 리스트에 추가

        return currentIndex;                      // 채널 번호 리턴
    }

    // 채널을 채널 목록에서 제거
    public static synchronized void removeChannel(int channelIndex) {
        if (channelsList.containsKey("GameChannel" + channelIndex)) {
            channelsList.remove("GameChannel" + channelIndex);       // 채널 목록에서 채널 제거
            availableIndexes.add(channelIndex);      // 제거된 채널의 인덱스를 사용 가능 인덱스에 추가
        }
    }

    // 해당 index의 채널 반환
    public static Channel getChannel(String channelName) {
        return channelsList.get(channelName);
    }

    // channelsList를 순회하면서 각 channel의 정보를 문자열 리스트로 반환하는 메소드
    public static List<String> getChannelsInfo() {
        return channelsList.entrySet().stream()
                .map(entry -> "{\"channelIndex\":" + Integer.parseInt(entry.getKey().substring(11)) +
                        ", \"channelName\":\"" + entry.getValue().getChannelName() +
                        "\", \"isOnGame\":" + entry.getValue().getIsOnGame() +
                        ", \"mapName\":\"" + entry.getValue().getMapName() +
                        "\", \"channelUser\":" + entry.getValue().getChannelUserList().size() + 
                        ", \"gameMode\":\""+ entry.getValue().getGameMode() + "\"}")
                .collect(Collectors.toList());
    }
}
