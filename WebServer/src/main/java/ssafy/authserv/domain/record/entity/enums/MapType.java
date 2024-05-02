package ssafy.authserv.domain.record.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MapType {
    MEXICO("Mexico"), CEBU("Cebu"), UPHILL("Uphill");

    private final String mapName;


    // 주어진 ordinal로부터 MapType 이름 조회
    public static String getMapNameByOrdinal(int ordinal) {
        if (ordinal >= 0 && ordinal < values().length) {
            return values()[ordinal].getMapName();
        }
        throw new IllegalArgumentException("Invalid mapNum");
    }

    public static int getOrdinalByMapName(String mapName) throws RuntimeException {

        try {
            return MapType.valueOf(mapName).ordinal();
        } catch (IllegalArgumentException e){
            throw new RuntimeException("Can't find the mapName: " + e);
        }
    }
}
