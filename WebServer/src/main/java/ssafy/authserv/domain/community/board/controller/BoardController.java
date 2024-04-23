package ssafy.authserv.domain.community.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ssafy.authserv.domain.community.board.dto.requestdto.BoardModifyRequestDto;
import ssafy.authserv.domain.community.board.dto.requestdto.BoardRequestDto;
import ssafy.authserv.domain.community.board.dto.responsedto.BoardResponseDto;
import ssafy.authserv.domain.community.board.dto.responsedto.SuccessResponseDto;
import ssafy.authserv.domain.community.board.service.BoardService;
import ssafy.authserv.global.jwt.security.MemberLoginActive;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    // 게시판 글 작성
    @PostMapping("/create")
    public BoardResponseDto createPost(@AuthenticationPrincipal MemberLoginActive memberLoginActive, @RequestBody BoardRequestDto requestDto) {
        UUID memberId = memberLoginActive.id();
        return boardService.createPost(requestDto, memberId);
    }

    // 전체 게시글 조회
    @GetMapping("/all")
    public List<BoardResponseDto> getAllPost() {
        return boardService.getAllPost();
    }

    // 단일 게시글 조회
    @GetMapping("/{boardId}")
    public BoardResponseDto getPost(@PathVariable("boardId") Integer boardId) {
        return boardService.getPost(boardId);
    }

    // 게시글 수정
    @PutMapping("/{boardId}")
    public BoardResponseDto updatePost(@AuthenticationPrincipal MemberLoginActive memberLoginActive, @PathVariable("boardId") Integer boardId, @RequestBody BoardModifyRequestDto requestDto) {
        UUID memberId = memberLoginActive.id(); // 현재 사용자의 memberId를 가져옴
        return boardService.updatePost(boardId, requestDto, memberId);
    }

    // 게시글 삭제
    @DeleteMapping("/{boardId}")
    public SuccessResponseDto deletePost(@AuthenticationPrincipal MemberLoginActive memberLoginActive, @PathVariable("boardId") Integer boardId){
        UUID memberId = memberLoginActive.id(); // 현재 사용자의 memberId를 가져옴
        return boardService.deletePost(boardId, memberId);
    }
}
