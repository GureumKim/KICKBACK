package ssafy.authserv.domain.community.comment.dto.requestdto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentModifyRequestDto {
    private String commentContent;
    private Integer memberId;
    private LocalDateTime updatedTime;
}