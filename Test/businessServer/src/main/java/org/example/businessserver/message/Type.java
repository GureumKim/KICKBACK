package org.example.businessserver.message;

public enum Type {
    LIVESERVER, // udp 서버 식별
    INITIAL,    //최초 연결
    CREATE,     // 방 생성
    JOIN,       // 방 참가
    LEAVE,      // 방 나가기
    READY,      // 게임 준비
    START,      // 게임 시작
    TEST        // 테스트용
}
