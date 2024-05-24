package ssafy.authserv.domain.friendship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import ssafy.authserv.domain.friendship.dto.FriendReply;
import ssafy.authserv.domain.friendship.dto.FriendRequest;
import ssafy.authserv.domain.friendship.dto.FriendResponse;
import ssafy.authserv.domain.friendship.entity.Friendship;
import ssafy.authserv.domain.friendship.service.FriendshipService;
import ssafy.authserv.global.common.dto.Message;

@Controller
@RequiredArgsConstructor
public class FriendshipController {
    private final FriendshipService friendshipService;
    private final KafkaTemplate<String, FriendResponse> kafkaTemplate;

    @MessageMapping("/friend/request")
//    @SendToUser("/queue/friendship")
    public ResponseEntity<Message<FriendResponse>> handleCreateRequest(@Payload FriendRequest request, @Header("Authorization") String Authorization){
        FriendResponse response = friendshipService.sendFriendRequest(request.requesterId(), request.receiverName());

//        kafkaTemplate.send("friendship", response.messageReceiverId().toString(), response);
        kafkaTemplate.send("friendshipRequest", response.messageReceiverId().toString(), response);

        return ResponseEntity.ok().body(Message.success(response));
    }

    @MessageMapping("/friend/reply")
//    @SendToUser("/queue/friendship")
    public ResponseEntity<Message<Void>> handleUpdateRequest(@Payload FriendReply reply) {
        FriendResponse response = friendshipService.updateFriendshipStatus(reply);

        kafkaTemplate.send("friendshipUpdate", response.messageReceiverId().toString(), response);
        return ResponseEntity.ok().body(Message.success());
    }

}
