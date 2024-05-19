package org.example.businessserver.object;

import lombok.Getter;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Channel {
    private static int MAX_USERS = 6;         // 최대 사용자 수 (채널장 포함)
    private  List<String> channelUserList;    // 채널 내 사용자 목록
    private  String channelName;              // 채널 이름
    private String channelManager;            // 채널장
    private Boolean isOnGame;                 // 게임 중 여부
    private String mapName;                   // 선택된 맵
    private  List<Boolean> isReady;           // 사용자 준비 상태를 저장하는 리스트
    private String gameMode;                  // 게임 모드
    private List<Integer> teamColor;          // team 선택 : 0 - 오렌지팀, 1 - 블루팀
    private List<Integer> userCharacter;      // 캐릭터  (0~9)
    private ConcurrentHashMap<String,Session> sessionList; // 유저 목록

    // 로비 또는 라이브 채널 생성자
    public Channel(String channelName) {
        this.channelName = channelName;
        this.sessionList = new ConcurrentHashMap<>();
    }

    // 게임 채널 생성자
    public Channel(String channelName, String userName, String mapName, String gameMode) {
        this.channelName = channelName;
        this.channelManager = userName;
        this.mapName = mapName;
        this.isOnGame = false;                  // 초기엔 게임 중이 아님
        this.channelUserList = new ArrayList<>();  // 사용자 목록 초기화
        this.channelUserList.add(userName);        // 채널장을 사용자 목록에 추가
        // [true,true,true,true,true,true] 배열 생성
        this.isReady = new ArrayList<>(Collections.nCopies(MAX_USERS, true));
        this.gameMode = gameMode;
        this.teamColor = new ArrayList<>(Collections.nCopies(MAX_USERS, 2));
        this.userCharacter = new ArrayList<>(Collections.nCopies(MAX_USERS, 0));
        this.sessionList = new ConcurrentHashMap<>();
        isReady.set(0, true);                 // 채널장은 채널 생성 시 자동으로 준비 상태로 설정
    }

    // 게임 시작 메소드
    public void gameStart() {
        isOnGame = true;
    }

    // 사용자 추가 메소드
    public void addUser(String userName) {
        if (channelUserList.size() < MAX_USERS) {
            channelUserList.add(userName);
        }
    }

    // 맵 바꾸는 메소드
    public void changeMapName(String choiceMap) {
        mapName = choiceMap;
    }

    // 사용자의 준비 상태 변경
    public void setUserReady(String userName) {
        int index = channelUserList.indexOf(userName);
        if (index != -1) {
            if (isReady.get(index)) {
                isReady.set(index, false);
            } else {
                isReady.set(index,true);
            }
        }
    }

    // 사용자의 팀 상태 변경
    public void setTeamColor(String userName) {
        int index = channelUserList.indexOf(userName);
        if (index != -1) {
            if (teamColor.get(index) == 0) {
                teamColor.set(index, 1);
            } else {
                teamColor.set(index,0);
            }
        }
    }

    // 사용자 캐릭터 변경
    public void setCharacter(String userName, int characterIndex) {
        int index = channelUserList.indexOf(userName);
        if (index != -1) {
            userCharacter.set(index, characterIndex);
        }
    }

    // 모든 사용자가 준비되었는지 확인
    public boolean isAllReady() {
        return isReady.stream().allMatch(ready -> ready);
    }

    // 사용자 제거 메소드
    public void removeUser(String userName, int channelIndex) throws IOException {
        int index = channelUserList.indexOf(userName);
        boolean removed = channelUserList.remove(userName);

        if (removed) {
            isReady.set(index,true);                           // 사용자 제거 시 준비 상태도 제거
            teamColor.set(index, 2);
            userCharacter.set(index, 0);
            if (Objects.equals(userName, channelManager)) {    // 채널장이 나간 경우
                if (!channelUserList.isEmpty()) {              // 채널장이 나간 후 채널에 유저가 남아있는 경우
                    channelManager = channelUserList.get(0);
                } else {                                       // 채널장이 나간 후 채널에 유저가 없는 경우
                    Channels.removeChannel(channelIndex);
                }
            }
        }
    }

    public Session getUserSession(String username) {
        return sessionList.get(username);
    }

    // 채널에 유저 추가
    public void addUserSession(String userName, Session session) {
        sessionList.put(userName, session);
    }

    //채널에 있는 유저 제거
    public void removeUserSession(String userName) {
        sessionList.remove(userName);
    }

    // userSessions의 모든 키 값을 새로운 배열에 담아 반환하는 메소드
    public Set<String> getSessionsName() {
        return new HashSet<>(this.sessionList.keySet());
    }

    // 참여자 정보 조회
    public Set<Session> getSessionsInfo() {
        return new HashSet<>(this.sessionList.values());
    }
}

