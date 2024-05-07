package ssafy.authserv.domain.community.board.dto.requestdto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardModifyRequestDto {
    private String title;
    private String content;
    private LocalDateTime updatedDate;
    private String category;

}
