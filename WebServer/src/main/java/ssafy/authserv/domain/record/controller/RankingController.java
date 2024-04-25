package ssafy.authserv.domain.record.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ssafy.authserv.domain.record.dto.SoccerRankingInfo;
import ssafy.authserv.domain.record.service.RankingService;

@RestController
@RequestMapping("/api/v1/ranking")
@RequiredArgsConstructor
public class RankingController {
    private final RankingService rankingService;

    @GetMapping("/soccer")
    public ResponseEntity<Page<SoccerRankingInfo>> getRanking(@RequestParam(defaultValue = "1") int page) {
        Page<SoccerRankingInfo> rankingData = rankingService.getSoccerRanking(page-1);

        return ResponseEntity.ok().body(rankingData);
    }


}
