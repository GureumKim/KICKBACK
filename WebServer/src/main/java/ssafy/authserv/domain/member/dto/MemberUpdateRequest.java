package ssafy.authserv.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

@Schema(description = "프로필 업데이트 dto")
public record MemberUpdateRequest(
        @Schema(description = "닉네임", example = "JohnDoe")
        @Size(max = 6, message = "닉네임은 6자 이하여야 합니다.")
        @Pattern(regexp = "^[^\"}',\\\\\\[\\]:]*$", message = "닉네임에는 다음 문자들을 사용할 수 없습니다: \", }', , \\, ', [, ], :")
        String nickname,
        @Schema(description = "파일", type = "string", format = "binary")

        MultipartFile profileImage
) {
}
