package ssafy.authserv.domain.community.comment.dto.requestdto;

import lombok.Getter;

@Getter
public class CommentRequestDto {
    private String content;
    private Integer memberId;
    private Integer boardId;
}