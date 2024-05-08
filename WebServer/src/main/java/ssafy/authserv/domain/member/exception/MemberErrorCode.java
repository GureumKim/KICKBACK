package ssafy.authserv.domain.member.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode {
    EXIST_USER_EMAIL(HttpStatus.INTERNAL_SERVER_ERROR, "이미 가입된 이메일 입니다."),
    EXIST_USER_NICKNAME(HttpStatus.INTERNAL_SERVER_ERROR, "이미 존재하는 닉네임 입니다."),
    NOT_FOUND_USER(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    ALREADY_MEMBER_LOGIN(HttpStatus.FORBIDDEN, "이미 로그인된 유저입니다."),
    ALREADY_MEMBER_LOGOUT(HttpStatus.BAD_REQUEST, "이미 로그아웃된 유저입니다."),
    CURRENT_CHANGE_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "변경하려는 비밀번호가 기존과 동일합니다."),
    PASSWORD_CONFIRMATION_MISMATCH(HttpStatus.BAD_REQUEST, "확인 비밀 번호가 불일치 합니다."),
    REDIS_NOT_TOKEN(HttpStatus.UNAUTHORIZED, "로그인 시간이 만료되었습니다. 다시 로그인해주세요."),
    MEMBER_NOT_LOGIN(HttpStatus.INTERNAL_SERVER_ERROR, "다시 로그인해주세요");


    private final HttpStatus httpStatus;
    private final String errorMessage;
}
