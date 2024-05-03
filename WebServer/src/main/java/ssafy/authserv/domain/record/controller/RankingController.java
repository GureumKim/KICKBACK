package ssafy.authserv.domain.record.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.authserv.domain.record.dto.BetaSpeedRankingInfo;
import ssafy.authserv.domain.record.dto.SpeedRankingInfo;
import ssafy.authserv.domain.record.entity.enums.MapType;
import ssafy.authserv.domain.record.service.RankingService;
import ssafy.authserv.global.common.dto.Message;

@RestController
@RequestMapping("/api/v1/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

//    @GetMapping("/soccer")
//    public ResponseEntity<Page<SoccerRankingInfo>> getSoccerRanking(@RequestParam(defaultValue = "1") int page) {
//        Page<SoccerRankingInfo> rankingData = rankingService.getSoccerRanking(page-1);
//
//        return ResponseEntity.ok().body(rankingData);
//    }

    @GetMapping("/speed")
    public ResponseEntity<Page<SpeedRankingInfo>> getSpeedRanking(@RequestParam(defaultValue = "1") int mapNum, @RequestParam(defaultValue = "1") int page) {
        Page<SpeedRankingInfo> rankingData = rankingService.getSpeedRanking(mapNum, page-1);

        return ResponseEntity.ok().body(rankingData);
    }

    @GetMapping("/search")
    public ResponseEntity<Message<BetaSpeedRankingInfo>> searchMemberRanking (@RequestParam(defaultValue = "0") int mapNum, @RequestParam String nickname) {

        BetaSpeedRankingInfo info = rankingService.getMemberSpeedRanking(mapNum, nickname);

        return ResponseEntity.ok().body(Message.success(info));
    }

    @GetMapping("/search2")
    public ResponseEntity<Message<BetaSpeedRankingInfo>> searchMemberRanking2 (@RequestParam(defaultValue = "Mexico") String mapName, @RequestParam String nickname) {
        int mapNum = MapType.getOrdinalByName(mapName);

        BetaSpeedRankingInfo info = rankingService.getMemberSpeedRanking(mapNum, nickname);

        return ResponseEntity.ok().body(Message.success(info));
    }
}
