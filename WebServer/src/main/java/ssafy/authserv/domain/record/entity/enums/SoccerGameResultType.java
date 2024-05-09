package ssafy.authserv.domain.record.entity.enums;

public enum SoccerGameResultType {
    WIN, DRAW, LOSE;

    public static SoccerGameResultType fromName(String result) {
        return SoccerGameResultType.valueOf(result.toUpperCase());
    }
}