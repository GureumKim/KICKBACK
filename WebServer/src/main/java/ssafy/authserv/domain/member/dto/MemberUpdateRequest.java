package ssafy.authserv.domain.member.dto;

import org.springframework.web.multipart.MultipartFile;

public record MemberUpdateRequest(
        String nickname,
        MultipartFile profileImage
) {
}
