package ssafy.authserv.domain.community.notice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ssafy.authserv.domain.community.notice.dto.requestdto.NoticeModifyRequestDto;
import ssafy.authserv.domain.community.notice.dto.requestdto.NoticeRequestDto;
import ssafy.authserv.domain.community.notice.dto.responsedto.NoticeResponseDto;
import ssafy.authserv.domain.community.notice.dto.responsedto.SuccessResponseDto;
import ssafy.authserv.domain.community.notice.entity.Notice;
import ssafy.authserv.domain.community.notice.entity.NoticeCategory;
import ssafy.authserv.domain.community.notice.repository.NoticeRepository;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.entity.enums.MemberRole;
import ssafy.authserv.domain.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;

    // 공지사항 작성
    @Transactional
    public NoticeResponseDto createNotice(@RequestBody NoticeRequestDto requestDto, UUID memberId) {
        // memberId를 사용하여 member 엔티티 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member Not found"));

        // 멤버가 admin 권한이 있는지 검사하기
        if (member.getRole() != MemberRole.ADMIN) {
            throw new RuntimeException("관리자만 게시글을 작성할 수 있습니다.");
        }

        Notice notice = Notice.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .category(requestDto.getCategory())
                .member(member)
                .build();

        noticeRepository.save(notice);

        return new NoticeResponseDto(notice);
    }

    // 전체 공지사항 조회
    @Transactional
    public List<NoticeResponseDto> getAllNotice() {
        return noticeRepository.findAll().stream().map(NoticeResponseDto::new).collect(Collectors.toList());
    }

    // 특정 공지사항 조회
    @Transactional
    public NoticeResponseDto getNotice(Integer id) {
        return noticeRepository.findById(id).map(NoticeResponseDto::new).orElseThrow(
                () -> new IllegalArgumentException("공지사항이 존재하지 않습니다."));
    }

    // 공지사항 수정
    @Transactional
    public NoticeResponseDto updateNotice(Integer id, @RequestBody NoticeModifyRequestDto requestDto, UUID memberId) {

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + "번 게시물이 존재하지 않습니다."));

        if (!notice.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 게시글을 수정할 수 있는 권한이 없습니다.");
        }

        notice.setTitle(requestDto.getTitle());
        notice.setContent(requestDto.getContent());
        notice.setCategory(Enum.valueOf(NoticeCategory.class, requestDto.getCategory()));
        notice.setUpdatedAt(LocalDateTime.now());

        return new NoticeResponseDto(noticeRepository.save(notice));
    }

    // 게시물 삭제
    @Transactional
    public SuccessResponseDto deleteNotice(Integer id, UUID memberId) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id + "번 게시물이 존재하지 않습니다."));

        if (!notice.getMember().getId().equals(memberId)) {
            throw new IllegalArgumentException("해당 게시물을 삭제할 수 있는 권한이 없습니다.");

        }
        noticeRepository.deleteById(id);

        return new SuccessResponseDto(notice);
    }
}
