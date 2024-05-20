package ssafy.authserv.domain.community.comment.dto.requestdto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentModifyRequestDto {
    private String content;
    private Integer memberId;
    private LocalDateTime updatedAt;
}