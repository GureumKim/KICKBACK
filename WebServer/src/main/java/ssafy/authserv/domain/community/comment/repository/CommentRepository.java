package ssafy.authserv.domain.community.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.authserv.domain.community.comment.entity.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findAllByBoardId(int boardId);
}
