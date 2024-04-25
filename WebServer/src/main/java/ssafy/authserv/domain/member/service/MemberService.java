package ssafy.authserv.domain.member.service;

import ssafy.authserv.domain.member.dto.*;
import ssafy.authserv.domain.member.entity.Member;

import java.util.UUID;

public interface MemberService {

    Member signup(SignupRequest signupRequest);

    LoginResponse login(LoginRequest loginRequest);

    /**
     * 로그아웃을 처리합니다.
     *
     * @param email 로그아웃할 회원의 이메일
     */
    void logout(String email);

    MemberInfo getMember(UUID memberId);

    void deleteMember(UUID memberId);

    MemberUpdateResponse updateProfile(UUID memberId, MemberUpdateRequest updateRequest);

    void updatePassword(UUID memberId, PasswordChangeRequest passwordChangeRequest);
}
