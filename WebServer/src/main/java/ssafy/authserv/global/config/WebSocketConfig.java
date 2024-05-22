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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ssafy.authserv.global.jwt.JwtTokenProvider;
import ssafy.authserv.global.jwt.exception.JwtErrorCode;
import ssafy.authserv.global.jwt.exception.JwtException;
import ssafy.authserv.global.jwt.security.MemberLoginActive;

import java.io.PrintWriter;
import java.nio.file.AccessDeniedException;

@Configuration
@RequiredArgsConstructor
@Slf4j
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue");
        registry.setApplicationDestinationPrefixes("/app")
                .setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000")
                .withSockJS();
    }

    /**
     * Not annotated parameter overrides @NonNullApi paramete warning 해결
     * 참조. https://stackoverflow.com/questions/56685326/not-annotated-parameter-overrides-parameter
     */
    @Override
    public void configureClientInboundChannel(@NonNull ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(@NonNull Message<?> message, @NonNull MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
                if (StompCommand.CONNECT.equals(accessor.getCommand())) {
                    String authToken = accessor.getFirstNativeHeader("Authorization");
                    if (authToken != null) {
                        // 그냥 Bearer prefix 쓰는게 좋은 듯...
                        // prefix로 필터링 가능
                        // 나중 프로젝트에서는 그렇게 적용하자
                        if (authToken.startsWith("Bearer ")) {
                            try {
                                authToken = authToken.substring(7);
                                MemberLoginActive member = jwtTokenProvider.resolveAccessToken(authToken);

                                if (member != null) {

                                    if (jwtTokenProvider.isBlockedAccessToken(member.email(), authToken)) {
                                        throw new JwtException(JwtErrorCode.EXPIRED_TOKEN);
                                    }

                                    SecurityContextHolder.getContext()
                                            .setAuthentication(jwtTokenProvider.createAuthenticationToken(member));
                                }
                            } catch (JwtException e) {
                                SecurityContextHolder.clearContext();
                                log.info("JWT 검증 실패: {}", e.getErrorCode().getErrorMessage());

//                                throw new AccessDeniedException("Failed to access");
                                throw new JwtException(e.getErrorCode());
                            }
                        }
                    }
                }
                return message;
            }
        });
    }

}
