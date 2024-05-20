package ssafy.authserv.global.jwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="권한 확인", description = "토큰을 통해 권한을 확인하기 위한 API")
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class TokenValidationController {

    @Operation(
            summary = "권한 확인",
            description = "사용자의 access token이 유효한지 확인하기 위한 API입니다."
    )
    @GetMapping("/check/authenticated")
    @PreAuthorize("hasAnyAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Void> isAuthenticated(){

        return ResponseEntity.ok().body(null);
    }

}
