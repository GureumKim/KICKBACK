package ssafy.authserv.domain.community.notice.controller;

import lombok.RequiredArgsConstructor;
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

@RestController
@RequestMapping("/api/v1/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    // 공지사항 작성
    @PostMapping("/create")
    @PreAuthorize("hasAuthority('ADMIN')")
    public NoticeResponseDto createNotice(@AuthenticationPrincipal MemberLoginActive memberLoginActive, @RequestBody NoticeRequestDto requestDto) {
        UUID memberId = memberLoginActive.id();
        return noticeService.createNotice(requestDto, memberId);
    }

    // 전체 게시글 조회
    @GetMapping("/all")
    public List<NoticeResponseDto> getAllNotice() {
        return noticeService.getAllNotice();
    }

    // 단일 게시글 조회
    @GetMapping("/{noticeId}")
    public NoticeResponseDto getNotice(@PathVariable("noticeId") Integer noticeId) {
        return noticeService.getNotice(noticeId);
    }

    // 게시글 수정
    @PutMapping("/{noticeId}")
    public NoticeResponseDto updateNotice(@AuthenticationPrincipal MemberLoginActive memberLoginActive, @PathVariable("noticeId") Integer noticeId, @RequestBody NoticeModifyRequestDto requestDto) {
        UUID memberId = memberLoginActive.id();
        return noticeService.updateNotice(noticeId, requestDto, memberId);
    }

    // 게시글 삭제
    @DeleteMapping("/{noticeId}")
    public SuccessResponseDto deleteNotice(@AuthenticationPrincipal MemberLoginActive memberLoginActive, @PathVariable("noticeId") Integer noticeId) {
        UUID memberId = memberLoginActive.id();
        return noticeService.deleteNotice(noticeId, memberId);
    }
}
