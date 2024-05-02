package ssafy.authserv.domain.member.dto;

import jakarta.validation.constraints.Size;

public record CheckNickname(
        @Size(max = 6, message = "닉네임은 6자 이하여야 합니다.")
        String nickname
) {
}
