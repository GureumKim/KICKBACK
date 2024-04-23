package ssafy.authserv.domain.community.comment.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.authserv.domain.community.comment.entity.Comment;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CommentResponseDto {
    private Integer id;
    private String commentContent;
    private String nickname;
    private LocalDateTime createdTime;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.commentContent = comment.getCommentContent();
        this.nickname = comment.getMember().getNickname();
        this.createdTime = comment.getCreatedTime();
    }

}
