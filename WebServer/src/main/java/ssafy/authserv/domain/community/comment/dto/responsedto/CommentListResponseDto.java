package ssafy.authserv.domain.community.comment.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.authserv.domain.community.comment.entity.Comment;

@Getter
@NoArgsConstructor
public class CommentListResponseDto {
    private Integer id;
    private Integer boardId;
    private String nickname;
    private String content;


    public CommentListResponseDto(Comment comment) {
        this.id = comment.getId();
        this.boardId = comment.getBoard().getId();
        this.nickname = comment.getMember().getNickname();
        this.content = comment.getContent();
    }
}
