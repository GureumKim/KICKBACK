package ssafy.authserv.domain.community.comment.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentListResponseDto {
    private Integer id;
    private Integer boardId;
    private String commentContent;

    public CommentListResponseDto(Integer id, Integer boardId, String commentContent) {
        this.id = id;
        this.boardId = boardId;
        this.commentContent = commentContent;
    }
}
