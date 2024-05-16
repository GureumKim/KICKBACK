package ssafy.authserv.domain.community.comment.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ssafy.authserv.domain.community.board.entity.Board;
import ssafy.authserv.domain.community.board.repository.BoardRepository;
import ssafy.authserv.domain.community.comment.dto.requestdto.CommentModifyRequestDto;
import ssafy.authserv.domain.community.comment.dto.requestdto.CommentRequestDto;
import ssafy.authserv.domain.community.comment.dto.responsedto.CommentListResponseDto;
import ssafy.authserv.domain.community.comment.dto.responsedto.CommentResponseDto;
import ssafy.authserv.domain.community.comment.dto.responsedto.SuccessCommentResponseDto;
import ssafy.authserv.domain.community.comment.entity.Comment;
import ssafy.authserv.domain.community.comment.repository.CommentRepository;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    // 댓글 생성
    @Transactional
    public CommentResponseDto createComment(Integer boardId, UUID memberId, CommentRequestDto commentRequestDto) {

        // memberId를 사용하여 Member 엔티티 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 게시판 번호로 게시글 찾기
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .member(member)
                .content(commentRequestDto.getContent())
                .board(board)
                .build();

        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public SuccessCommentResponseDto deleteComment(Integer id, UUID memberId) {

        // 해당 번호의 댓글이 있는지 조회하기
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + "번 댓글이 존재하지 않습니다."));

        // 댓글을 쓴 사람이 댓글을 삭제하는지 조회
        if (!comment.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 댓글을 삭제할 수 있는 권한이 없습니다.");
        }

        commentRepository.deleteById(id);
        return new SuccessCommentResponseDto(comment);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Integer id, UUID memberId, @RequestBody CommentModifyRequestDto commentModifyRequestDto) {
        // 해당 번호의 댓글이 있는지 조회하기
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + "번 댓글이 존재하지 않습니다."));

        // 댓글을 쓴 사람이 댓글을 수정하는지 조회
        if (!comment.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 댓글을 수정할 수 있는 권한이 없습니다.");
        }

        comment.setContent(commentModifyRequestDto.getContent());
        comment.setUpdatedAt(LocalDateTime.now());
        return new CommentResponseDto(comment);

    }

    // 게시글 별 댓글 조회
    @Transactional
    public List<CommentListResponseDto> listComment(Integer boardId) {
        // 게시글 찾기
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 해당 게시글에 있는 댓글 목록 조회하기
        List<Comment> comments = board.getComments();

        // 각 comment를 CommentListResponseDto로 매핑함
        return commentRepository.findAllByBoardId(boardId).stream().map(CommentListResponseDto::new).collect(Collectors.toList());

    }
}
