package ssafy.authserv.domain.member.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.entity.enums.MemberRole;

@Getter
@Setter
@AllArgsConstructor
public class SignupRequest {

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식이 옳바르지 않습니다.")
    private String email;

    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8-16자리로 구성해주세요. 영어 대소문자, 숫자, 특수문자를 각각 1개 이상 씩 포함해야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수 입력 값입니다.")
    @Pattern(regexp = "^[^\"}',\\\\\\[\\]:]*$", message = "닉네임에는 다음 문자들을 사용할 수 없습니다: \", }', , \\, ', [, ], :")
    @Size(max = 6, message = "닉네임은 6자 이하여야 합니다.")
    private String nickname;

    private String profileImage;

    public Member toEntity() {
        return Member.builder()
                .role(MemberRole.USER)
                .email(email)
                .password(password)
                .nickname(nickname)
                .profileImage(profileImage)
                .build();
    }
}
