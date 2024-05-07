package ssafy.authserv.domain.community.notice.dto.requestdto;

import lombok.Getter;
import ssafy.authserv.domain.community.notice.entity.NoticeCategory;

import java.time.LocalDateTime;

@Getter
public class NoticeRequestDto {
    private String title;
    private String content;
    private String category;

    public NoticeCategory getCategory() {
        return NoticeCategory.fromName(this.category);
    }

    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
}
