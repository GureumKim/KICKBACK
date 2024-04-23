package ssafy.authserv.domain.community.board.dto.requestdto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardRequestDto {
    private String title;
    private String content;
    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;
}