package ssafy.authserv.domain.community.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.authserv.domain.community.notice.dto.responsedto.NoticeWithNavigation;
import ssafy.authserv.domain.community.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
    @Query("SELECT n FROM Notice n JOIN FETCH n.member WHERE n.id = :id")
    Notice findNoticeById(@Param("id") Integer id);

    @Query("SELECT n.id FROM Notice n WHERE n.id < :id ORDER BY n.id DESC LIMIT 1")
    Integer findPreviousNoticeId(@Param("id") Integer id);

    @Query("SELECT n.id FROM Notice n WHERE n.id > :id ORDER BY n.id ASC LIMIT 1")
    Integer findNextNoticeId(@Param("id") Integer id);
}
