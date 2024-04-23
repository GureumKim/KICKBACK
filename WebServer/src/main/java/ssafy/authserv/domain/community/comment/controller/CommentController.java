package ssafy.authserv.domain.community.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ssafy.authserv.domain.community.comment.dto.requestdto.CommentModifyRequestDto;
import ssafy.authserv.domain.community.comment.dto.requestdto.CommentRequestDto;
import ssafy.authserv.domain.community.comment.dto.responsedto.CommentListResponseDto;
import ssafy.authserv.domain.community.comment.dto.responsedto.CommentResponseDto;
import ssafy.authserv.domain.community.comment.dto.responsedto.SuccessCommentResponseDto;
import ssafy.authserv.domain.community.comment.service.CommentService;
import ssafy.authserv.global.jwt.security.MemberLoginActive;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping("/{boardId}")
    public CommentResponseDto createComment(@PathVariable("boardId") Integer boardId, @AuthenticationPrincipal MemberLoginActive memberLoginActive, @RequestBody CommentRequestDto commentRequestDto) {
        UUID memberId = memberLoginActive.id();
        return commentService.createComment(boardId, memberId, commentRequestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/{commentId}")
    public SuccessCommentResponseDto deleteComment(@PathVariable("commentId") Integer commentId, @AuthenticationPrincipal MemberLoginActive memberLoginActive) {
        UUID memberId = memberLoginActive.id();
        return commentService.deleteComment(commentId, memberId);
    }

    // 댓글 수정
    @PutMapping("/{commentId}")
    public CommentResponseDto updateComment(@PathVariable("commentId") Integer commentId, @AuthenticationPrincipal MemberLoginActive memberLoginActive, @RequestBody CommentModifyRequestDto commentModifyRequestDto) {
        UUID memberId = memberLoginActive.id();
        return commentService.updateComment(commentId, memberId, commentModifyRequestDto);
    }

    // 게시글별 댓글 조회
    @GetMapping("/read/{boardId}")
    public List<CommentListResponseDto> listComment(@PathVariable("boardId") Integer boardId) {
        return commentService.listComment(boardId);
    }

}
