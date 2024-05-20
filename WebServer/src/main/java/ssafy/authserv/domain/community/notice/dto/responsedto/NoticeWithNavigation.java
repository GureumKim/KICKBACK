package ssafy.authserv.domain.community.notice.dto.responsedto;

import ssafy.authserv.domain.community.notice.entity.Notice;


public record NoticeWithNavigation(
        Notice currentNotice,
        Integer prevNoticeId,
        Integer nextNoticeId
){}
