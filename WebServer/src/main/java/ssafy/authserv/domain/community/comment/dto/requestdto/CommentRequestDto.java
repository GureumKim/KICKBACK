package ssafy.authserv.domain.community.comment.dto.requestdto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String commentContent;
    private Integer memberId;
    private Integer boardId;
}