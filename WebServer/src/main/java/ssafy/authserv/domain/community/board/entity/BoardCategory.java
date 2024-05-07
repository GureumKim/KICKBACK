package ssafy.authserv.domain.community.board.entity;

public enum BoardCategory {
    FREE, SHARE, QNA;

    public static BoardCategory fromName(String boardCategory) {
        return BoardCategory.valueOf(boardCategory.toUpperCase());
    }
}
