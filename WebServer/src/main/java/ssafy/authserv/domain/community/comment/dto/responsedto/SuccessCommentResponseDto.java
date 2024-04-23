package ssafy.authserv.domain.community.comment.dto.responsedto;

import lombok.Getter;

@Getter
public class SuccessCommentResponseDto {
    private boolean success;

    public SuccessCommentResponseDto(boolean success) {
        this.success = success;
    }
}
