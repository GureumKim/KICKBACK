package ssafy.authserv.domain.community.board.dto.responsedto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.authserv.domain.community.board.entity.Board;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
public class BoardResponseDto {
    private Integer id;
    private String title;
    private String content;
    private UUID memberId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.memberId = board.getMember().getId();
        this.createdDate = board.getCreatedDate();
        this.updatedDate = board.getUpdatedDate();
    }
}