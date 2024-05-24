package ssafy.authserv.domain.friendship.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ssafy.authserv.domain.friendship.dto.FriendReply;
import ssafy.authserv.domain.friendship.dto.FriendResponse;
import ssafy.authserv.domain.friendship.entity.Friendship;
import ssafy.authserv.domain.friendship.entity.enums.FriendshipStatus;
import ssafy.authserv.domain.friendship.repository.FriendshipRepository;
import ssafy.authserv.domain.member.entity.Member;
import ssafy.authserv.domain.member.exception.MemberErrorCode;
import ssafy.authserv.domain.member.exception.MemberException;
import ssafy.authserv.domain.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final MemberRepository memberRepository;
    private final FriendshipRepository friendshipRepository;
    private final NotificationService notificationService;

//    private final KafkaTemplate<String, FriendResponse> kafkaTemplate;
    private final SimpMessagingTemplate messagingTemplate;

//    @KafkaListener(topics = "friend-requests", groupId = "friend-notification")
//    public void consumeFriendRequest(String message) {
//        FriendRequest request = parseFriendRequest(message); // 메시지 파싱 로직 구현 필요
//        // 웹소켓을 통해 수신자에게 실시간 알림을 전송합니다.
//        messagingTemplate.convertAndSendToUser(
//                request.getReceiverNickname(),
//                "/queue/notifications",
//                new NotificationMessage("You have a new friend request from " + request.getSenderNickname())
//        );
//    }

    @Override
    @Transactional
    public FriendResponse sendFriendRequest(UUID requesterId, String receiverName) {

        Member requester = memberRepository.findById(requesterId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_USER));
        Member receiver = memberRepository.findByNickname(receiverName)
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_USER));

        LocalDateTime now = LocalDateTime.now();

        Friendship friendship = Friendship.builder()
                .me(requester)
                .friend(receiver)
                .status(FriendshipStatus.PENDING)
                .createdAt(now)
                .updatedAt(now)
                .isRequester(true) // 보낸사람
                .build();
        friendshipRepository.save(friendship);

//        messagingTemplate.convertAndSendToUser(
//                receiver.getId().toString(),
//                "/queue/friendship",
//                new FriendResponse(receiver.getNickname(), requester.getNickname(), "PENDING")
//        );


        FriendResponse response = new FriendResponse(receiver.getId(), requester.getNickname(), receiver.getNickname(), "PENDING");
//        kafkaTemplate.send("friendshipRequest",  response);
//        messagingTemplate.convertAndSendToUser(response.messageReceiverId().toString(), "/queue/friendship",
//                response);
        return response;
    }

    @Override
    @Transactional
    public FriendResponse updateFriendshipStatus(FriendReply accept) {
        Friendship requesterFriendship = friendshipRepository.findByFriendNicknameAndMeNickname(accept.receiver(), accept.requester())
                .orElseThrow(() -> new RuntimeException("Friendship not found"));

        FriendshipStatus status = FriendReply.getStatus(accept.status());
        if (status.equals(FriendshipStatus.ACCEPTED)){
            requesterFriendship.setStatus(FriendshipStatus.ACCEPTED);
        } else if (status.equals(FriendshipStatus.REJECTED)) {
            requesterFriendship.setStatus(FriendshipStatus.REJECTED);
        } else {
            return null;
        }

        LocalDateTime now = LocalDateTime.now();
        Member receiver = memberRepository.findByNickname(accept.receiver())
                .orElseThrow(() ->  new MemberException(MemberErrorCode.NOT_FOUND_USER));
        Member requester = memberRepository.findByNickname(accept.requester())
                .orElseThrow(() -> new MemberException(MemberErrorCode.NOT_FOUND_USER));

        Friendship receiverFriendship = Friendship.builder()
                .me(receiver)
                .friend(requester)
                .status(status)
                .createdAt(now)
                .updatedAt(now)
                .isRequester(false) // 받은 사람
                .build();


        requesterFriendship.setUpdatedAt(now);
        friendshipRepository.save(requesterFriendship);
        friendshipRepository.save(receiverFriendship);

//        messagingTemplate.convertAndSendToUser(
//                requester.getId().toString(),
//                "/queue/friendship",
//                new FriendResponse(receiver.getNickname(), requester.getNickname(), accept.status())
//        );

        FriendResponse response =  new FriendResponse(requester.getId(), receiver.getNickname(), requester.getNickname(), "PENDING");
//        notificationService.processFriendRequestReply(response);
//        messagingTemplate.convertAndSendToUser(response.messageReceiverId().toString(), "/queue/friendship",
//                response);
        return response;
    }
}
