package ssafy.authserv.domain.record.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ssafy.authserv.domain.record.dto.UpdateSpeedRecordRequest;
import ssafy.authserv.domain.record.dto.UpdateSpeedRecordRequest2;
import ssafy.authserv.domain.record.entity.enums.MapType;
import ssafy.authserv.domain.record.service.RecordService;
import ssafy.authserv.global.common.dto.Message;
import ssafy.authserv.global.jwt.security.MemberLoginActive;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/record")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PutMapping("/updateSpeedRecord")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Message<Void>> updateSpeedRecord(@AuthenticationPrincipal MemberLoginActive loginActive, @RequestBody UpdateSpeedRecordRequest request) {
        recordService.updateSpeedRecord(loginActive.id(), request.mapName(), request.time());

        return ResponseEntity.ok().body(Message.success());
    }

    @PutMapping("/updateSpeedRecord2")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Message<Void>> updateSpeedRecord2(@AuthenticationPrincipal MemberLoginActive loginActive, @RequestBody UpdateSpeedRecordRequest2 request) {

//        int mapNum = MapType.getOrdinalByName(request.mapName());
        recordService.updateSpeedRecord(loginActive.id(), request.mapName(), request.time());

        return ResponseEntity.ok().body(Message.success());
    }

    @PostMapping("/test")
    ResponseEntity<Message<Void>> testCreateSpeedRankings(@RequestParam UUID memberId, @RequestParam String map, @RequestParam String time) {
        recordService.updateSpeedRecord(memberId, map, time);

        return ResponseEntity.ok().body(Message.success());
    }
}
