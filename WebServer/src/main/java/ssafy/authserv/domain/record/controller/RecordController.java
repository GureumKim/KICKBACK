package ssafy.authserv.domain.record.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.authserv.domain.record.dto.UpdateSpeedRecordRequest;
import ssafy.authserv.domain.record.dto.UpdateSpeedRecordRequest2;
import ssafy.authserv.domain.record.entity.enums.MapType;
import ssafy.authserv.domain.record.service.RecordService;
import ssafy.authserv.global.common.dto.Message;
import ssafy.authserv.global.jwt.security.MemberLoginActive;

@RestController
@RequestMapping("/api/v1/record")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping("/updateSpeedRecord")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Message<Void>> updateSpeedRecord(@AuthenticationPrincipal MemberLoginActive loginActive, @RequestBody UpdateSpeedRecordRequest request) {
        recordService.updateSpeedRecord(loginActive.id(), request.map(), request.time());

        return ResponseEntity.ok().body(Message.success());
    }

    @PostMapping("/updateSpeedRecord2")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<Message<Void>> updateSpeedRecord2(@AuthenticationPrincipal MemberLoginActive loginActive, @RequestBody UpdateSpeedRecordRequest2 request) {

        int mapNum = MapType.getOrdinalByMapName(request.mapName());
        recordService.updateSpeedRecord(loginActive.id(), mapNum, request.time());

        return ResponseEntity.ok().body(Message.success());
    }

//    @PostMapping("/test/{map}/{time}")
//    ResponseEntity<Message<Void>> testCreateSpeedRankings(@AuthenticationPrincipal MemberLoginActive loginActive, @PathVariable int map, @PathVariable float time) {
//        recordService.saveSpeedRecord(loginActive.id(), map, time);
//
//        return ResponseEntity.ok().body(Message.success());
//    }
}
