package ssafy.authserv.domain.friendship.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ssafy.authserv.domain.friendship.dto.FriendRequest;
import ssafy.authserv.domain.friendship.dto.FriendRequestEvent;
import ssafy.authserv.domain.friendship.dto.FriendResponse;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.exception.MemberErrorCode;
import ssafy.authserv.domain.member.exception.MemberException;
import ssafy.authserv.domain.member.repository.MemberRepository;

import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;
    private final MemberRepository memberRepository;

    // messageReceiverId는 알림 응답 받는 대상
    @KafkaListener(topics = "friendshipRequest", groupId = "friendship-service")
    public void processFriendRequest(FriendResponse response) {
        log.info("Processing friend request from {} to {}", response.sender(), response.receiver());

        String destination = String.format("/topic/friendship-%s/request", response.messageReceiverId().toString().replace("-", ""));
//        String destination = "/queue/friendship";
//        String destination = "/user/queue/friendship";
//        messagingTemplate.convertAndSendToUser(response.messageReceiverId().toString(),destination, response);
        messagingTemplate.convertAndSend(destination, response);
        log.info("보냄: {}", response.messageReceiverId());
        log.info("{}", destination);
    }

    @KafkaListener(topics = "friendshipUpdate", groupId = "friendship-service")
    public void processFriendRequestReply(FriendResponse response) {
        log.info("Processing friendship update. Requester: {}, Receiver: {}, result: {}", response.receiver(), response.sender(), response.status());

        log.info("요기 id: {}", response.messageReceiverId());
//        messagingTemplate.convertAndSendToUser(response.messageReceiverId().toString(), "/queue/friendship", response );
    }
}
