package ssafy.authserv.domain.member.dto;

import jakarta.validation.constraints.Email;

public record CheckEmail(
        @Email(message = "이메일 형식이 아닙니다.")
        String email
) {
}
