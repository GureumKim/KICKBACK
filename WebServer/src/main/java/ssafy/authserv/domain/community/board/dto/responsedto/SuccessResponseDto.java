package ssafy.authserv.domain.community.board.dto.responsedto;

import lombok.Getter;

// 게시글 삭제 시 성공 여부를 담아서 보낼 DTO
@Getter
public class SuccessResponseDto {
    private boolean success;

    public SuccessResponseDto(boolean success) {
        this.success = success;
    }
}
