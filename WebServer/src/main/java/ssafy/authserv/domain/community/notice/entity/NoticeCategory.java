package ssafy.authserv.domain.community.notice.entity;

public enum NoticeCategory {
    NOTICE, UPDATE;

    public static NoticeCategory fromName(String noticeCategory) {
        return NoticeCategory.valueOf(noticeCategory.toUpperCase());
    }
}
