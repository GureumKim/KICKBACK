package ssafy.authserv.domain.member.dto;

public record MemberUpdateRequest(
        String nickname,
        String profileImage
) {
}
