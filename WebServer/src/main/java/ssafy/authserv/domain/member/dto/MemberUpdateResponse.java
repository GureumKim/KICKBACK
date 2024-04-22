package ssafy.authserv.domain.member.dto;

public record MemberUpdateResponse(
        String nickname,
        String profileImage
) {
}
