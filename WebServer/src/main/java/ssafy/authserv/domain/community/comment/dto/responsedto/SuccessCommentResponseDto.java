package ssafy.authserv.domain.community.comment.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.authserv.domain.community.comment.entity.Comment;

@Getter
@NoArgsConstructor
public class SuccessCommentResponseDto {
    private Integer id;
    private String commentContent;

    public SuccessCommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.commentContent = comment.getContent();
    }
}
