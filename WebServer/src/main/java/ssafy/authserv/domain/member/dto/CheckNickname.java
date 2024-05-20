package ssafy.authserv.domain.member.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CheckNickname(
        @Size(max = 6, message = "닉네임은 6자 이하여야 합니다.")
        @Pattern(regexp = "^[^\"}',\\\\\\[\\]:]*$", message = "닉네임에는 다음 문자들을 사용할 수 없습니다: \", }', , \\, ', [, ], :")
        String nickname
) {
}
