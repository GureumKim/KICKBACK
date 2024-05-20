package ssafy.authserv.domain.community.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ssafy.authserv.domain.community.board.dto.requestdto.BoardModifyRequestDto;
import ssafy.authserv.domain.community.board.dto.requestdto.BoardRequestDto;
import ssafy.authserv.domain.community.board.dto.responsedto.BoardResponseDto;
import ssafy.authserv.domain.community.board.dto.responsedto.SuccessResponseDto;
import ssafy.authserv.domain.community.board.entity.Board;
import ssafy.authserv.domain.community.board.entity.BoardCategory;
import ssafy.authserv.domain.community.board.repository.BoardRepository;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService{

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    // 게시글 작성
    @Transactional
    public BoardResponseDto createPost(@RequestBody BoardRequestDto boardRequestDto, UUID memberId) {
        // memberId를 사용하여 Member 엔티티 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .category(boardRequestDto.getCategory())
                .member(member)
                .build();

        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    // 전체 게시글 조회
    @Transactional
    public List<BoardResponseDto> getAllPost() {
        return boardRepository.findAll().stream().map(BoardResponseDto::new).collect(Collectors.toList());
    }

    // 특정 게시글 조회
    @Transactional
    public BoardResponseDto getPost(Integer id) {
        return boardRepository.findById(id).map(BoardResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
    }

    // 게시글 수정
    @Transactional
    public BoardResponseDto updatePost(Integer id, @RequestBody BoardModifyRequestDto requestDto, UUID memberId) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + " 번 게시물이 존재하지 않습니다."));

        if (!board.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 게시글을 수정할 수 있는 권한이 없습니다.");
        }

        board.setTitle(requestDto.getTitle());
        board.setContent(requestDto.getContent());
        board.setCategory(Enum.valueOf(BoardCategory.class, requestDto.getCategory()));
        board.setUpdatedAt(LocalDateTime.now());

        return new BoardResponseDto(boardRepository.save(board));
    }

    // 게시글 삭제
    @Transactional
    public SuccessResponseDto deletePost(Integer id, UUID memberId) {

        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + " 번 게시물이 존재하지 않습니다."));

        if (!board.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 게시글을 삭제할 수 있는 권한이 없습니다.");
        }

        boardRepository.deleteById(id);

        return new SuccessResponseDto(board);
    }
}