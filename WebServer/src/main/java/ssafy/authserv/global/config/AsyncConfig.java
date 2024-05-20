package ssafy.authserv.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(3); // 기본 스레드 수
        executor.setMaxPoolSize(30); // 최대 스레드 개수
        executor.setQueueCapacity(50); // 이벤트 대기 큐 크기
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); // 태스크 처리량이 스레드 갯수가 max로 채워지구 queue 대기수를 넘겼을 때 RejectExecutionException 발생
        // CallerRunsPolicy : shut down 상태가 아니라며 요청한 Caller Thread에서 직접 처리
        // 태스크 유실 최소화하기 위해 이 구현체 사용
        executor.setThreadNamePrefix("Executor-");
        return executor;
    }

}
