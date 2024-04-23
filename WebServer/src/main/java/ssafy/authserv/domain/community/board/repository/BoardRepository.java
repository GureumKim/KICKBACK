package ssafy.authserv.domain.community.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.authserv.domain.community.board.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
}