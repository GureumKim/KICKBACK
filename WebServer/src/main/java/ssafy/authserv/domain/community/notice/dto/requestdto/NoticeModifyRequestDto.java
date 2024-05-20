package ssafy.authserv.domain.community.notice.dto.requestdto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class NoticeModifyRequestDto {
    private String title;
    private String content;
    private LocalDateTime updatedAt;
    private String category;
}
