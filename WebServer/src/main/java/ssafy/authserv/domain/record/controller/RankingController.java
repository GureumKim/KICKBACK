package ssafy.authserv.domain.record.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ssafy.authserv.domain.record.dto.CreateSpeedRecordRequest;
import ssafy.authserv.domain.record.dto.SoccerRankingInfo;
import ssafy.authserv.domain.record.dto.SpeedRankingInfo;
import ssafy.authserv.domain.record.service.RankingService;
import ssafy.authserv.domain.record.service.RecordService;
import ssafy.authserv.global.common.dto.Message;
import ssafy.authserv.global.jwt.security.MemberLoginActive;

@RestController
@RequestMapping("/api/v1/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

    @GetMapping("/soccer")
    public ResponseEntity<Page<SoccerRankingInfo>> getSoccerRanking(@RequestParam(defaultValue = "1") int page) {
        Page<SoccerRankingInfo> rankingData = rankingService.getSoccerRanking(page-1);

        return ResponseEntity.ok().body(rankingData);
    }

    @GetMapping("/speed")
    public ResponseEntity<Page<SpeedRankingInfo>> getSpeedRanking(@RequestParam(defaultValue = "1") int mapNum, @RequestParam(defaultValue = "1") int page) {
        Page<SpeedRankingInfo> rankingData = rankingService.getSpeedRanking(mapNum, page-1);

        return ResponseEntity.ok().body(rankingData);
    }
}
