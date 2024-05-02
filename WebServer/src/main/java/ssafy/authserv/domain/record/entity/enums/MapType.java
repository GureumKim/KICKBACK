package ssafy.authserv.domain.record.entity.enums;

public enum MapType {
    ONE, TWO, THREE;

    // 주어진 ordinal로부터 MapType 이름 조회
    public static String getMapNameByOrdinal(int ordinal) {
        if (ordinal >= 0 && ordinal < values().length) {
            return values()[ordinal].name();
        }
        throw new IllegalArgumentException("Invalid mapNum");
    }

    public static int getOrdinalByMapName(String mapName) {
        return MapType.valueOf(mapName).ordinal();
    }
}
