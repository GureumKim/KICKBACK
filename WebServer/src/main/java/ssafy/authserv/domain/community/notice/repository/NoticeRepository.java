package ssafy.authserv.domain.community.notice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.authserv.domain.community.notice.entity.Notice;

public interface NoticeRepository extends JpaRepository<Notice, Integer> {
}
