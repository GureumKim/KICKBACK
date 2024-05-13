package ssafy.authserv.domain.member.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ssafy.authserv.domain.member.dto.*;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.exception.MemberErrorCode;
import ssafy.authserv.domain.member.exception.MemberException;
import ssafy.authserv.domain.member.repository.MemberRepository;
import ssafy.authserv.domain.record.service.RecordService;
import ssafy.authserv.global.component.firebase.FirebaseService;
import ssafy.authserv.global.jwt.repository.RefreshTokenRepository;
import ssafy.authserv.global.jwt.service.JwtTokenService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@Transactional // 클래스 수준에서 @Transactional 어노테이션을 사용하면, 해당 클래스의 모든 public 메서드에 대해 트랜잭션이 적용됩니다.
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenRepository refreshTokenRepository;

    // 테스트

    @Override
    public Member signup(SignupRequest signupRequest) {
        if (memberRepository.existsByEmail(signupRequest.getEmail())) {
            throw new MemberException(MemberErrorCode.EXIST_USER_EMAIL);
        }
        if (memberRepository.existsByNickname(signupRequest.getNickname())) {
            throw new MemberException(MemberErrorCode.EXIST_USER_NICKNAME);
        }

        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));


       return memberRepository.save(signupRequest.toEntity());

    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        Member member = memberRepository.findByEmail(loginRequest.email()).orElseThrow(()
                -> new MemberException(MemberErrorCode.NOT_FOUND_USER));

//        if (refreshTokenRepository.find(loginRequest.email()).isPresent()) {
//            throw new MemberException(MemberErrorCode.ALREADY_MEMBER_LOGIN);
//        }

        String realPassword = member.getPassword();

        if (!passwordEncoder.matches(loginRequest.password(), realPassword)) {
            throw new MemberException(MemberErrorCode.NOT_MATCH_PASSWORD);
        }

        return jwtTokenService.issueAndSaveTokens(member);
    }

//    @Override
//    public LoginResponse login(LoginRequest loginRequest) {
//        Member member = memberRepository.findByEmail(loginRequest.email())
//                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_USER));
//
//        String password = member.getPassword();
//        Optional<String> token = refreshTokenRepository.find(loginRequest.email());
//
//        if (!passwordEncoder.matches(loginRequest.password(), password)) {
//            throw new MemberException(MemberErrorCode.NOT_MATCH_PASSWORD);
//        }
//
//
//        if (token.isEmpty()){
//            return jwtTokenService.issueAndSaveTokens(member);
//        } else {
//            throw new MemberException(MemberErrorCode.ALREADY_MEMBER_LOGIN);
//        }
//    }

    @Override
    public void logout(String email) {
        Optional<String> token = refreshTokenRepository.find(email);

        if (token.isEmpty()) {
            throw new MemberException(MemberErrorCode.ALREADY_MEMBER_LOGOUT);
        }

        refreshTokenRepository.delete(email);

    }

    @Override
    public MemberInfo getMember(UUID userId) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_USER));

        return MemberInfo.builder()
//                .id(member.getId())
                .role(member.getRole())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .profileImage(member.getProfileImage())
                .build();
    }

    @Override
    public void deleteMember(UUID userId) {
        memberRepository.deleteById(userId);

    }


    private final FirebaseService firebaseService;
    @Override
    public MemberUpdateResponse updateProfile(UUID userId, MemberUpdateRequest request) {
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_USER));
        try {
            if (request.profileImage() == null && request.nickname() == null)
                throw new RuntimeException("바뀐 항목이 없습니다.");

            if (request.profileImage() != null){
                String imageUrl = firebaseService.uploadImage(request.profileImage(), userId);
                member.setProfileImage(imageUrl);
            }
            if (request.nickname() != null){
                member.setNickname(request.nickname());
                memberRepository.save(member);
            }
            return new MemberUpdateResponse(member.getNickname(), member.getProfileImage());
        } catch(IOException e) {
            log.info("== 업로드 실패: {}", e.toString());
            throw new RuntimeException("upload failed: ", e);
        }
    }

    @Override
    public void updatePassword(UUID userId, PasswordChangeRequest request){
        Member member = memberRepository.findById(userId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_USER));

        String password = member.getPassword();

        // 현재 비밀 번호 제대로 입력했는지 확인
        if (!passwordEncoder.matches(request.currentPassword(), password)){
            throw new MemberException(MemberErrorCode.NOT_MATCH_PASSWORD);
        }

        // 현재 비밀번호와 변경하려는 비밀번호 같은 지 확인
        if (passwordEncoder.matches(password,request.updatedPassword())) {
            throw new MemberException(MemberErrorCode.CURRENT_CHANGE_MATCH_PASSWORD);
        }

        // 비밀번호 변경과 확인용 비밀번호 같은 지 확인
        if (!request.updatedPassword().equals(request.checkPassword())) {
            throw new MemberException(MemberErrorCode.PASSWORD_CONFIRMATION_MISMATCH);
        }

        member.updatePassword(passwordEncoder.encode(request.updatedPassword()));
    }

    @Override
    public Boolean isEmailDuplicated(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public Boolean isNicknameDuplicated(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}
