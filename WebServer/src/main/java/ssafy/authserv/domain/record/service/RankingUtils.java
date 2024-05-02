package ssafy.authserv.domain.record.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ssafy.authserv.domain.member.entity.Member;

import java.time.Duration;

@Component
@Slf4j
public class RankingUtils {
    public String millisToString(Long millis){
//        Duration duration = Duration.ofMillis(millis);
//        log.info("키드밀리 {}",duration);

//        long minutes = duration.toMinutesPart();

        long intPart = millis / 1000;

        long minutes = intPart / 60;
//        long seconds = duration.toSeconds();
        long seconds = intPart % 60;
//        long milliSec = duration.toMillisPart();
        long milliSec = millis % 1000;

        return String.format("%02d", minutes) + ":" +
                String.format("%02d", seconds) + ":" +
                String.format("%02d", milliSec);
    }

    public long stringToMillis(String timeString){
        String[] parts = timeString.split(":");
        long minutes = Long.parseLong(parts[0]);
        long seconds = Long.parseLong(parts[1]);
        long milliseconds = Long.parseLong(parts[2]);

        Duration duration = Duration.ofMinutes(minutes).plusSeconds(seconds).plusMillis(milliseconds);

        return duration.toMillis();
    }
}
