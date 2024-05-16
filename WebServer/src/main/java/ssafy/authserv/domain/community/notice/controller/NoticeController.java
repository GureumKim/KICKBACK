package ssafy.authserv.domain.community.notice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ssafy.authserv.domain.community.notice.dto.requestdto.NoticeModifyRequestDto;
import ssafy.authserv.domain.community.notice.dto.requestdto.NoticeRequestDto;
import ssafy.authserv.domain.community.notice.dto.responsedto.NoticeResponseDto;
import ssafy.authserv.domain.community.notice.dto.responsedto.SuccessResponseDto;
import ssafy.authserv.domain.community.notice.service.NoticeService;
import ssafy.authserv.global.jwt.security.MemberLoginActive;

import java.util.List;
import java.util.UUID;

@Tag(name="공지사항", description = "공지사항 관련 API")
@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;

    // 공지사항 작성
    @Operation(summary = "공지사항 작성", description = "memberRole이 ADMIN인 사람만 글을 작성할 수 있습니다.")
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public NoticeResponseDto createNotice(@AuthenticationPrincipal MemberLoginActive memberLoginActive, @RequestBody NoticeRequestDto requestDto) {
        UUID memberId = memberLoginActive.id();
        return noticeService.createNotice(requestDto, memberId);
    }

    // 전체 게시글 조회
    @Operation(summary = "전체 공지사항 조회")
    @GetMapping("/all")
    public List<NoticeResponseDto> getAllNotice() {
        return noticeService.getAllNotice();
    }

    // 단일 게시글 조회
    @Operation(summary = "개별 공지사항 조회")
    @GetMapping("/{noticeId}")
    public NoticeResponseDto getNotice(@PathVariable("noticeId") Integer noticeId) {
        return noticeService.getNotice(noticeId);
    }

    // 게시글 수정
    @Operation(summary = "공지사항 수정")
    @PutMapping("/{noticeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public NoticeResponseDto updateNotice(@AuthenticationPrincipal MemberLoginActive memberLoginActive, @PathVariable("noticeId") Integer noticeId, @RequestBody NoticeModifyRequestDto requestDto) {
        UUID memberId = memberLoginActive.id();
        return noticeService.updateNotice(noticeId, requestDto, memberId);
    }

    // 게시글 삭제
    @Operation(summary = "공지사항 삭제")
    @DeleteMapping("/{noticeId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public SuccessResponseDto deleteNotice(@AuthenticationPrincipal MemberLoginActive memberLoginActive, @PathVariable("noticeId") Integer noticeId) {
        UUID memberId = memberLoginActive.id();
        return noticeService.deleteNotice(noticeId, memberId);
    }
}
