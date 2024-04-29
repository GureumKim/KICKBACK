package ssafy.authserv.domain.member.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ssafy.authserv.domain.member.dto.*;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.service.MemberService;
import ssafy.authserv.domain.record.entity.SoccerRecord;
import ssafy.authserv.domain.record.repository.SoccerRecordRepository;
import ssafy.authserv.domain.record.service.RecordService;
import ssafy.authserv.global.common.dto.Message;
import ssafy.authserv.global.component.firebase.FirebaseService;
import ssafy.authserv.global.jwt.repository.RefreshTokenRepository;
import ssafy.authserv.global.jwt.security.MemberLoginActive;
import ssafy.authserv.global.jwt.service.JwtTokenService;

import java.io.IOException;

@Tag(name="회원", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenService jwtTokenService;
    private final RecordService recordService;

    @Operation(
            summary = "회원가입",
            description = "email, password, nickname 정보를 받아 회원 가입을 진행합니다."
    )
    @PostMapping("/signup")
    public ResponseEntity<Message<Void>> signup(@Valid @RequestBody SignupRequest request) {
        Member member = memberService.signup(request);
        recordService.saveSoccerRecord(member);

        return ResponseEntity.ok().body(Message.success());
    }


//    @Operation(
//            summary = "로그인",
//            description = "email과 password를 통해 로그인을 합니다."
//    )
//    @PostMapping("/login")
//    public ResponseEntity<Message<LoginResponse>> login(@RequestBody LoginRequest request, HttpServletResponse response) {
//        LoginResponse loginResponse = memberService.login(request);
//
//        // JWT 토큰을 쿠키에 저장
//        Cookie accessTokenCookie = new Cookie("accessToken", loginResponse.jwtToken().accessToken());
//        accessTokenCookie.setPath("/");
//        accessTokenCookie.setMaxAge(25200); // 4200분(25200초)으로 설정 (25200)
//        response.addCookie(accessTokenCookie);
//        return ResponseEntity.ok().body(Message.success(loginResponse));
//    }

    @Operation(
            summary = "로그인",
            description = "email과 password를 통해 로그인을 합니다."
    )
    @PostMapping("/login")
    public ResponseEntity<Message<LoginResponse>> login(@RequestBody LoginRequest request, HttpServletResponse response) {
        LoginResponse loginResponse = memberService.login(request);

//        // JWT 토큰을 쿠키에 저장
        Cookie accessTokenCookie = new Cookie("accessToken", loginResponse.jwtToken().accessToken());
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(25200); // 4200분(25200초)으로 설정 (25200)
        accessTokenCookie.setHttpOnly(true); // JavaScript를 통한 접근 방지
//        accessTokenCookie.setSecure(true); // HTTPS를 통해서만 쿠키 전송
        response.addCookie(accessTokenCookie);


        response.addHeader("accessToken", loginResponse.jwtToken().accessToken());
        response.addHeader("refreshToken",
                loginResponse.jwtToken().refreshToken());
        return ResponseEntity.ok()
//                .header("accessToken", loginResponse.jwtToken().accessToken())
//                .header("refreshToken",
//                        loginResponse.jwtToken().refreshToken())
                .body(Message.success(loginResponse));
    }

    @Operation(
            summary = "로그아웃"
    )
    @PostMapping("/logout")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Message<Void>> logout(@AuthenticationPrincipal MemberLoginActive loginActive, HttpServletResponse response){

        memberService.logout(loginActive.email());
        // 쿠키 삭제
        // 쿠키를 직접 삭제하는 API는 없기에 해당 쿠키의 유효기간을 0으로 설정하거나
        // 과거의 날짜로 설정하여 즉시 만료되도록 구현
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setMaxAge(0);
        accessTokenCookie.setPath("/");
        response.addCookie(accessTokenCookie);
        return ResponseEntity.ok().body(Message.success());
    }

    @Operation(
            summary = "유저 정보 조회",
            description = "유저가 자신의 정보를 확인합니다."
    )
    @GetMapping("/get")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Message<MemberInfo>> getMember(@AuthenticationPrincipal MemberLoginActive loginActive) {
        MemberInfo info = memberService.getMember(loginActive.id());

        return ResponseEntity.ok().body(Message.success(info));
    }

    // delete 시 refreshtoken 도 남아 있으면 delte시키거나 관련해서
    // 로직 구현하기!! (보안!!)
    @Operation(
            summary = "회원탈퇴",
            description = "회원 탈퇴를 진행합니다."
    )
    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Message<Void>> deleteMember(@AuthenticationPrincipal MemberLoginActive loginActive) {
        memberService.deleteMember(loginActive.id());
        return ResponseEntity.ok().body(Message.success());
    }


    @Operation(
            summary = "비밀번호 변경"
    )
    @PatchMapping("/password/change")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Message<Void>> updatePassword(@AuthenticationPrincipal MemberLoginActive loginActive,
                                                        @Valid @RequestBody PasswordChangeRequest request ) {
        memberService.updatePassword(loginActive.id(), request);
        return ResponseEntity.ok().body(Message.success());
    }

    /**
     * 지금은 variablepath에 이메일을 받아 수행하지만
     *  정석은 이메일로 인증코드 보내서 수행
     *  고도화로 도전할만 하다.
     *
     *  필요 기술 및 기능
     *  1. 인증 코드 만들기 및 저장(기억)
     *  2. 이메일 보내기
     *  3. 인증 코드 받아서 인증하기
     *  4. 이후 토큰 리프레시
     */
    @Operation(
            summary = "access 토큰 재발급",
            description ="memberEmail을 통해 access 토큰을 재발급합니다."
    )
    @PostMapping("/reissue/accessToken/{memberEmail}")
    public ResponseEntity<Message<String>> reissueAccessToken(@PathVariable String memberEmail) {
        String reissuedAccessToken = jwtTokenService.reissueAccessToken(memberEmail);
        return ResponseEntity.ok().body(Message.success(reissuedAccessToken));
    }

    @Operation(
            summary = "프로필 업데이트",
            description = "유저의 닉네임과 프로필 이미지를 업데이트합니다."
    )
    @PatchMapping("/update")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Message<MemberUpdateResponse>> updateProfile(@AuthenticationPrincipal MemberLoginActive loginActive, @ModelAttribute MemberUpdateRequest updateRequest){

       MemberUpdateResponse response = memberService.updateProfile(loginActive.id(), updateRequest);

       return ResponseEntity.ok().body(Message.success(response));
    }
}
