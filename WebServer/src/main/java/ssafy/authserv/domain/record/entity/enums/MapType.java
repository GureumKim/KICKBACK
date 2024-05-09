package ssafy.authserv.domain.record.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MapType {
    MEXICO("Mexico"), CEBU("Cebu"), DOWNHILL("Downhill");

    private final String mapName;


    // 주어진 ordinal로부터 MapType 이름 조회
    public static String getMapNameByOrdinal(int ordinal) {
        if (ordinal >= 0 && ordinal < values().length) {
            return values()[ordinal].getMapName();
        }
        throw new IllegalArgumentException("Invalid mapNum");
    }

    public static int getOrdinalByName(String mapName) throws RuntimeException {

        try {
            return MapType.valueOf(mapName.toUpperCase()).ordinal();
        } catch (IllegalArgumentException e){
            throw new RuntimeException("Can't find the mapName: " + e);
        }
    }

    private MapType fromName(String mapName) {
        return MapType.valueOf(mapName.toUpperCase());
    }
}
