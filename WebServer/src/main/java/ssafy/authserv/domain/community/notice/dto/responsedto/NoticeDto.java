package ssafy.authserv.domain.community.notice.dto.responsedto;

import jakarta.transaction.Transactional;
import ssafy.authserv.domain.community.notice.entity.Notice;
import ssafy.authserv.domain.community.notice.entity.NoticeCategory;

import java.time.LocalDateTime;

public record NoticeDto(
        Integer id,
        String title,
        String content,
        NoticeCategory category,
        String nickname,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Integer prevNoticeId,
        Integer nextNoticeId
) {

    public static NoticeDto getNoticeDto(NoticeWithNavigation noticeWithNavigation, Notice currentNotice) {
        Integer prev = noticeWithNavigation.prevNoticeId();
        Integer next = noticeWithNavigation.nextNoticeId();


        return new NoticeDto(
                currentNotice.getId(),
                currentNotice.getTitle(),
                currentNotice.getContent(),
                currentNotice.getCategory(),
                currentNotice.getMember().getNickname(),
                currentNotice.getCreatedAt(),
                currentNotice.getUpdatedAt(),
                prev,
                next
        );
    }
}
