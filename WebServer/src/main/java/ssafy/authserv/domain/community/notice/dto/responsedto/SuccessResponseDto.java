package ssafy.authserv.domain.community.notice.dto.responsedto;

import lombok.Getter;
import ssafy.authserv.domain.community.notice.entity.Notice;

@Getter
public class SuccessResponseDto {
    private Integer id;
    private String title;
    private String content;

    public SuccessResponseDto(Notice notice) {
        this.id = notice.getId();
        this.title = notice.getTitle();
        this.content = notice.getContent();
    }
}
