package ssafy.authserv.domain.record.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ssafy.authserv.domain.member.entity.Member;

import java.time.Duration;

@Component
@Slf4j
public class RankingUtils {
    // milli second 단위인 완주 기록을 String 타입의 특정 형식으로 변환합니다.
    public String millisToString(Long millis){

        long intPart = millis / 1000;

        long minutes = intPart / 60;
        long seconds = intPart % 60;
        long milliSec = millis % 1000;

        return String.format("%02d", minutes) + ":" +
                String.format("%02d", seconds) + ":" +
                String.format("%02d", milliSec);
    }

    // String 타입으로 들어온 완주 기록으 milli second 단위로 변환합니다.
    public long stringToMillis(String timeString){
        String[] parts = timeString.split(":");
        long minutes = Long.parseLong(parts[0]);
        long seconds = Long.parseLong(parts[1]);
        long milliseconds = Long.parseLong(parts[2]);

        Duration duration = Duration.ofMinutes(minutes).plusSeconds(seconds).plusMillis(milliseconds);

        return duration.toMillis();
    }
}
