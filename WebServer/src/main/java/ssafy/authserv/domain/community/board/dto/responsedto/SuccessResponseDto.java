package ssafy.authserv.domain.community.board.dto.responsedto;

import lombok.Getter;
import ssafy.authserv.domain.community.board.entity.Board;

// 게시글 삭제 시 성공 여부를 담아서 보낼 DTO
@Getter
public class SuccessResponseDto {
    private Integer id;
    private String title;
    private String content;

    public SuccessResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
    }
}
