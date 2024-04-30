package ssafy.authserv.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "프로필 업데이트 dto")
public record MemberUpdateRequest(
        @Schema(description = "닉네임", example = "JohnDoe")
        String nickname,
        @Schema(description = "파일", type = "string", format = "binary")

        MultipartFile profileImage
) {
}
