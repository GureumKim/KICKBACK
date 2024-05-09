package org.example.businessserver.message;


public enum Type {
    LIVESERVER(0),   // 최초 연결 (라이브 서버)
    CLIENT(1),      // 최초 연결 (유니티)
    CREATE(2),      // 방 생성
    JOIN(3),        // 방 참가
    LEVAE(4),       // 방 나가기
    READY(5),       // 게임 준비
    START(6),       // 게임 시작
    ITEM(7),        // 아이템 사용
    END(8),         // 게임 끝
    Map(9);         // 맵 바꾸기

    private final int idx;

    Type(int idx) { this.idx = idx; }

    public static Type findByIndex(int index) {
        for (Type type : Type.values()) {
            if (type.idx == index) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown index: " + index);
    }
}
