package org.example.businessserver.message;

public enum Type {
    LIVESERVER(0), // udp 서버 식별
    INITIAL(1),    //최초 연결
    CREATE(2),     // 방 생성
    JOIN(3),       // 방 참가
    LEAVE(4),      // 방 나가기
    READY(5),      // 게임 준비
    START(6),      // 게임 시작
    TEST(7);        // 테스트용

    private final int type;


    Type(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}

