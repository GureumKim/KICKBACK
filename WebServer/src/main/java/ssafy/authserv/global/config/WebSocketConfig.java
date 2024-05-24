package ssafy.authserv.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeHandler;
import ssafy.authserv.global.jwt.JwtTokenProvider;
import ssafy.authserv.global.jwt.exception.JwtErrorCode;
import ssafy.authserv.global.jwt.exception.JwtException;
import ssafy.authserv.global.jwt.security.MemberLoginActive;

import java.io.PrintWriter;
import java.util.Objects;


@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app");
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000", "http://localhost:3001", "http://localhost:3002")
                .withSockJS();
    }

    /**
     * Not annotated parameter overrides @NonNullApi paramete warning 해결
     * 참조. https://stackoverflow.com/questions/56685326/not-annotated-parameter-overrides-parameter
     */
    @Override
    public void configureClientInboundChannel(@NonNull ChannelRegistration registration) {
        registration.interceptors((new ChannelInterceptor() {

            @Override
            public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel){
                final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                String accessToken = accessor.getFirstNativeHeader("Authorization");

                switch (accessor.getCommand()){
                    case CONNECT:
                        if (accessToken != null && !accessToken.isEmpty()){
                            log.info("{}", accessToken);
                            try {
                                MemberLoginActive member = jwtTokenProvider.resolveAccessToken(accessToken.substring(7));
                                if (member == null) {
                                    log.error("인증된 사용자 정보를 찾을 수 없습니다.");
                                    throw new JwtException(JwtErrorCode.INVALID_CLAIMS);
                                } else {
                                    log.info("회원 ID : {} - 요청 시도: ", member.id());
                                    if (jwtTokenProvider.isBlockedAccessToken(member.email(), accessToken)) {
                                        throw new JwtException(JwtErrorCode.EXPIRED_TOKEN);
                                    }
                                }
                                SecurityContextHolder.getContext()
                                        .setAuthentication(jwtTokenProvider.createAuthenticationToken(member));
                            } catch (JwtException e) {
                                SecurityContextHolder.clearContext();
                                log.info("JWT 검증 실패: {}", e.getErrorCode().getErrorMessage());

                                throw new JwtException(e.getErrorCode());
                            }
                        }
                        break;
                    case SUBSCRIBE:
                        break;
                    case UNSUBSCRIBE:
                        break;
                }
                return message;
            }

        }));
//        registration.interceptors(new ChannelInterceptor() {
//            @Override
//            public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
//                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
//                log.info("!!!!!!!!!!!!{}!!!!!!!!!!!!", message);
//
//                SecurityContext securityContext = (SecurityContext) Objects.requireNonNull(accessor.getSessionAttributes()).get(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
//
//                if (securityContext != null) {
//                    Authentication authentication = securityContext.getAuthentication();
//                    if (authentication != null && authentication.isAuthenticated()) {
//                        SecurityContextHolder.getContext().setAuthentication(authentication);
//                        log.info("!!!가능가능 쌉가능!!");
//                    }
//                }
////
//                return message;
//            }
//        });
    }

}
